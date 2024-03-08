package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.ValidadorGuardadoHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.core.utils.primerHijo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import io.reactivex.Single

class AsignarHabilidadesUseCase(
    private val habilidadesRepository: HabilidadesRepository,
    private val habilidadesAsignaRepository: HabilidadesAsignaRepository,
    private val obtenerCampaniasUseCase: ObtenerCampaniasUseCase,
    private val personaRepository: RddPersonaRepository,
    private val sesionManager: SessionPersonRepository,
    private val sincronizadorAsignarHabilidades: SincronizadorAsignarHabilidades,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private val session by lazy { sesionManager.get() }


    companion object {
        const val LIMITE_HABILIDADES_SELECCIONADAS = 2
    }

    private lateinit var seleccionadorHabilidades: Seleccionador<Habilidad>
    private lateinit var validadorGuardadoHabilidades: ValidadorGuardadoHabilidades
    private lateinit var response: Response
    private lateinit var persona: PersonaRdd

    fun obtenerHabilidadesAsignadasAOtro(
        request: Request,
        subscriber: BaseSingleObserver<Response>
    ) {
        val single = Single.create<Response> { emitter ->
            val rolParaAsignar = checkNotNull(sesionManager.get()).rol.primerHijo()
                ?: error("Rol no soportado por no tener hijo")

            seleccionadorHabilidades =
                Seleccionador(
                    habilidadesRepository.obtener(rolParaAsignar),
                    LIMITE_HABILIDADES_SELECCIONADAS
                )

            persona = recuperarPersona(request, rolParaAsignar)

            validadorGuardadoHabilidades = ValidadorGuardadoHabilidades(seleccionadorHabilidades)

            configurarSeleccionadorHabilidades(persona)
            response = obtenerAsignarAOtroResponse()

            emitter.onSuccess(response)
        }

        execute(single, subscriber)
    }

    private fun recuperarPersona(
        request: Request,
        rolParaAsignar: Rol
    ): PersonaRdd {
        return requireNotNull(
            personaRepository.recuperarPersonaPorId(
                request.personaId,
                rolParaAsignar
            )
        )
    }


    fun obtenerHabilidadesAsignadasPropias(subscriber: BaseSingleObserver<Response>) {
        val single = Single.create<Response> { emitter ->
            seleccionadorHabilidades = Seleccionador(
                habilidadesRepository.obtener(session.rol),
                LIMITE_HABILIDADES_SELECCIONADAS
            )
            persona = session.persona as PersonaRdd
            validadorGuardadoHabilidades = ValidadorGuardadoHabilidades(seleccionadorHabilidades)
            configurarSeleccionadorHabilidades(persona)
            response = obtenerAsignarPropiasResponse()
            emitter.onSuccess(response)
        }
        execute(single, subscriber)
    }

    private fun configurarSeleccionadorHabilidades(persona: Persona) {
        val habilidadesSeleccionadasIds = habilidadesAsignaRepository
            .obtenerPorUA(persona.llaveUA, persona.unidadAdministrativa.campania.codigo)

        seleccionadorHabilidades.seleccionables.forEach {
            if (habilidadesSeleccionadasIds.contains(it.elemento.id)) {
                seleccionadorHabilidades.seleccionar(it)
            }
        }
    }

    fun cambiarSeleccionHabilidad(posicion: Int, subscriber: BaseSingleObserver<Response>) {
        val single = Single.create<Response> { emitter ->
            seleccionadorHabilidades.invertirSeleccion(posicion)

            emitter.onSuccess(obtenerResponse())
        }

        execute(single, subscriber)
    }

    fun asignarHabilidadesAOtro(subscriber: BaseSingleObserver<Unit>) {
        val single = Single.create<Unit> { emitter ->

            val sesion = requireNotNull(sesionManager.get())

            val request = HabilidadesAsignaRepository.Request(
                campania = persona.unidadAdministrativa.campania.codigo,
                codigoRegion = checkNotNull(persona.llaveUA.codigoRegion),
                codigoZona = persona.llaveUA.codigoZona,
                codigoSeccion = null,
                usuario = sesion.username,
                habilidades = seleccionadorHabilidades.seleccionados
                    .map { it.elemento.id })

            habilidadesAsignaRepository.guardar(request)
            emitter.onSuccess(Unit)
        }.doAfterSuccess {
            sincronizadorAsignarHabilidades
                .sincronizar()
                .subscribe({}, {})
        }

        execute(single, subscriber)
    }

    fun asignarHabilidadesPropias(subscriber: BaseSingleObserver<Unit>) {
        val single =
            obtenerCampaniasUseCase.recuperarCampaniaUsuarioLogueado().flatMap { campania ->
                Single.create<Unit> { emitter ->

                    val sesion = requireNotNull(sesionManager.get())

                    val request = HabilidadesAsignaRepository.Request(
                        campania = campania.codigo,
                        codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
                        codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
                        codigoSeccion = "-",
                        usuario = sesion.username,
                        habilidades = seleccionadorHabilidades.seleccionados
                            .map { it.elemento.id })

                    habilidadesAsignaRepository.guardar(request)
                    emitter.onSuccess(Unit)
                }.doOnSuccess {
                    sincronizadorAsignarHabilidades
                        .sincronizar()
                        .subscribe({}, {})
                }
            }

        execute(single, subscriber)
    }

    fun obtenerRol(): String {
        return requireNotNull(sesionManager.get()).rol.codigoRol
    }

    private fun esMaximaCantidadSeleccionada(): Boolean {
        return seleccionadorHabilidades.seleccionados.size == LIMITE_HABILIDADES_SELECCIONADAS
    }

    private fun obtenerAsignarAOtroResponse(): ResponseGerenteZona {
        return ResponseGerenteZona(
            habilidades = seleccionadorHabilidades.seleccionables,
            numeroSeleccionadas = seleccionadorHabilidades.seleccionados.size,
            persona = persona,
            esPosibleGuardar = validadorGuardadoHabilidades.validar(),
            esMaximoHabilidadesSeleccionado = esMaximaCantidadSeleccionada()
        )
    }

    private fun obtenerAsignarPropiasResponse(): ResponseGerenteRegional {
        return ResponseGerenteRegional(
            habilidades = seleccionadorHabilidades.seleccionables,
            numeroSeleccionadas = seleccionadorHabilidades.seleccionados.size,
            persona = persona,
            esPosibleGuardar = validadorGuardadoHabilidades.validar(),
            esMaximoHabilidadesSeleccionado = esMaximaCantidadSeleccionada()
        )
    }

    private fun obtenerResponse(): Response {
        response.habilidades = seleccionadorHabilidades.seleccionables
        response.numeroSeleccionadas = seleccionadorHabilidades.seleccionados.size
        response.persona = persona
        response.esPosibleGuardar = validadorGuardadoHabilidades.validar()
        response.esMaximoHabilidadesSeleccionado = esMaximaCantidadSeleccionada()
        return response
    }

    open class Response(
        var numeroMaximoHabilidades: Int = LIMITE_HABILIDADES_SELECCIONADAS,
        var numeroSeleccionadas: Int,
        var habilidades: List<Seleccionable<Habilidad>>,
        var persona: PersonaRdd,
        var esPosibleGuardar: Boolean,
        var esMaximoHabilidadesSeleccionado: Boolean
    )

    class Request(val personaId: Long)

    class ResponseGerenteZona(
        numeroMaximoHabilidades: Int = LIMITE_HABILIDADES_SELECCIONADAS,
        numeroSeleccionadas: Int,
        habilidades: List<Seleccionable<Habilidad>>,
        persona: PersonaRdd,
        esPosibleGuardar: Boolean,
        esMaximoHabilidadesSeleccionado: Boolean
    ) :
        Response(
            numeroMaximoHabilidades,
            numeroSeleccionadas,
            habilidades,
            persona,
            esPosibleGuardar,
            esMaximoHabilidadesSeleccionado
        )


    class ResponseGerenteRegional(
        numeroMaximoHabilidades: Int = LIMITE_HABILIDADES_SELECCIONADAS,
        numeroSeleccionadas: Int,
        habilidades: List<Seleccionable<Habilidad>>,
        persona: PersonaRdd,
        esPosibleGuardar: Boolean,
        esMaximoHabilidadesSeleccionado: Boolean
    ) :
        Response(
            numeroMaximoHabilidades,
            numeroSeleccionadas,
            habilidades,
            persona,
            esPosibleGuardar,
            esMaximoHabilidadesSeleccionado
        )

}
