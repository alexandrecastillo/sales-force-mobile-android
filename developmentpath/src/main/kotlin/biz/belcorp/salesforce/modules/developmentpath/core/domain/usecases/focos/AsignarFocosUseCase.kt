package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.GuardadoFocosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.ListadoFocosEnAsignacionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.ValidadorGuardado
import io.reactivex.Observable

class AsignarFocosUseCase(
    val campaniaRepository: CampaniasRepository,
    private val listadoFocosEnAsignacionRepository: ListadoFocosEnAsignacionRepository,
    private val guardadoFocosRepository: GuardadoFocosRepository,
    private val sincronizadorFocos: SincronizadorFocos,
    val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    companion object {
        const val LIMITE_FOCOS_SELECCIONADOS = 3
    }

    private lateinit var seleccionadorFocos: Seleccionador<Foco>
    private lateinit var seleccionadorPersonas: Seleccionador<PersonaRdd>
    private lateinit var validadorGuardadoFocos: ValidadorGuardado<Foco, PersonaRdd>
    private var seleccionSociasHabilitada = true
    private val sesion get() = requireNotNull(sesionManager.get())
    private val campania
        get() = requireNotNull(
            campaniaRepository.obtenerCampaniaActual(
                recuperarLlaveUa()
            )
        )

    fun obtener(subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->

            seleccionSociasHabilitada = true

            seleccionadorFocos = Seleccionador(
                listadoFocosEnAsignacionRepository.obtenerFocos(),
                LIMITE_FOCOS_SELECCIONADOS
            )

            seleccionadorPersonas = Seleccionador(obtenerPersonasPorRolDePadre())

            validadorGuardadoFocos = ValidadorGuardado(
                seleccionadorFocos,
                seleccionadorPersonas
            )

            emitter.onNext(generarResponse())
        }

        execute(observable, subscriber)
    }

    fun obtenerFocosPropios(subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->

            seleccionSociasHabilitada = true

            configurarSeleccionadorFocosPropio()

            seleccionadorPersonas = Seleccionador(mutableListOf())

            validadorGuardadoFocos = ValidadorGuardado(seleccionadorFocos, seleccionadorPersonas)

            emitter.onNext(generarResponsePropio())
        }

        execute(observable, subscriber)
    }

    fun obtenerPorPersona(personaId: Long, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->

            seleccionSociasHabilitada = false

            configurarSeleccionadorFocos(personaId)

            configurarSeleccionadorPersonas(personaId)

            validadorGuardadoFocos = ValidadorGuardado(seleccionadorFocos, seleccionadorPersonas)

            emitter.onNext(generarResponse())
        }

        execute(observable, subscriber)
    }

    private fun configurarSeleccionadorPersonas(personaId: Long) {
        seleccionadorPersonas =
            Seleccionador(obtenerPersonasPorRolDePadre())

        val indiceSociaSeleccionada =
            seleccionadorPersonas.seleccionables.indexOfFirst { it.elemento.id == personaId }

        seleccionadorPersonas.seleccionar(indiceSociaSeleccionada)
        seleccionadorPersonas.habilitarSolo(indiceSociaSeleccionada)
    }

    private fun obtenerPersonasPorRolDePadre(): List<PersonaRdd> {
        return when (sesion.rol) {
            Rol.DIRECTOR_VENTAS -> listadoFocosEnAsignacionRepository.obtenerGRs()
            Rol.GERENTE_REGION -> listadoFocosEnAsignacionRepository.obtenerGZs()
            Rol.GERENTE_ZONA -> listadoFocosEnAsignacionRepository.obtenerSocias()
            else -> throw UnsupportedRoleException(sesion.rol)
        }
    }

    private fun configurarSeleccionadorFocos(personaId: Long) {
        val focosSeleccionados = obtenerFocosDePersonaSegunPadre(personaId)

        seleccionadorFocos = Seleccionador(
            listadoFocosEnAsignacionRepository.obtenerFocos(),
            LIMITE_FOCOS_SELECCIONADOS
        )

        seleccionadorFocos.seleccionables.forEach {
            if (focosSeleccionados.contains(it.elemento.id)) {
                seleccionadorFocos.seleccionar(it)
            }
        }
    }

    private fun configurarSeleccionadorFocosPropio() {
        val focosSeleccionados = obtenerFocosPropio()
        val todosLosFocos = listadoFocosEnAsignacionRepository.obtenerFocos()

        seleccionadorFocos = Seleccionador(todosLosFocos, LIMITE_FOCOS_SELECCIONADOS)

        seleccionadorFocos.seleccionables.forEach {
            if (focosSeleccionados.contains(it.elemento.id)) {
                seleccionadorFocos.seleccionar(it)
            }
        }
    }

    private fun obtenerFocosPropio(): Array<Long> {
        return listadoFocosEnAsignacionRepository.obtenerFocosPorUA(sesion.llaveUA)
    }

    private fun obtenerFocosDePersonaSegunPadre(personaId: Long): Array<Long> {

        return when (val rolPadre = sesion.rol) {
            Rol.DIRECTOR_VENTAS ->
                listadoFocosEnAsignacionRepository.obtenerFocosIdGR(personaId)
            Rol.GERENTE_REGION ->
                listadoFocosEnAsignacionRepository.obtenerFocosIdGZ(personaId)
            Rol.GERENTE_ZONA ->
                listadoFocosEnAsignacionRepository.obtenerFocosIdSE(personaId)
            else ->
                throw UnsupportedRoleException(rolPadre)
        }
    }

    fun cambiarSeleccionFoco(posicion: Int, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->
            seleccionadorFocos.invertirSeleccion(posicion)

            emitter.onNext(generarResponse())
        }

        execute(observable, subscriber)
    }

    fun cambiarSeleccionFocoPropio(posicion: Int, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->
            seleccionadorFocos.invertirSeleccion(posicion)

            emitter.onNext(generarResponsePropio())
        }

        execute(observable, subscriber)
    }

    fun cambiarSeleccionSocia(posicion: Int, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->
            seleccionadorPersonas.invertirSeleccion(posicion)

            emitter.onNext(generarResponse())
        }

        execute(observable, subscriber)
    }

    fun guardar(subscriber: BaseObserver<Unit>) {
        val observable = Observable.create<Unit> { emitter ->
            guardarFocosDeEquipo()
            emitter.onNext(Unit)
        }.doAfterNext {
            sincronizadorFocos.sincronizar().subscribe({}, {})
        }

        execute(observable, subscriber)
    }

    fun guardarFocosPropio(subscriber: BaseObserver<Unit>) {
        val observable = Observable.create<Unit> { emitter ->
            guardarFocosPropios()
            emitter.onNext(Unit)
        }.doAfterNext {
            sincronizadorFocos.sincronizar().subscribe({}, {})
        }

        execute(observable, subscriber)
    }

    fun optenerRol(): String {
        return sesion.rol.codigoRol
    }

    private fun guardarFocosPropios() {
        val detalleFoco = DetalleFoco(
            campania = requireNotNull(campania.codigo),
            region = sesion.llaveUA.codigoRegion,
            zona = sesion.llaveUA.codigoSeccion,
            seccion = null,
            usuario = sesion.username,
            focos = idsFocosSeleccionados()
        )

        guardadoFocosRepository.guardar(listOf(detalleFoco))
    }

    private fun idsFocosSeleccionados(): Array<Long> {
        return seleccionadorFocos
            .seleccionados
            .map { it.elemento.id }
            .toTypedArray()
    }

    private fun guardarFocosDeEquipo() {
        val detalles = personasSeleccionadas().map { persona ->
            DetalleFoco(
                region = persona.llaveUA.codigoRegion,
                zona = persona.llaveUA.codigoZona,
                seccion = persona.llaveUA.codigoSeccion,
                campania = persona.unidadAdministrativa.campania.codigo,
                usuario = sesion.username,
                focos = idsFocosSeleccionados()
            )
        }

        guardadoFocosRepository.guardar(detalles)
    }

    private fun personasSeleccionadas(): List<Persona> {
        return seleccionadorPersonas.seleccionados.map { it.elemento }
    }

    private fun generarResponse(): Response {
        return Response(
            seleccionadorFocos.seleccionables,
            seleccionadorPersonas.seleccionables,
            validadorGuardadoFocos.validar(),
            seleccionSociasHabilitada,
            false
        )
    }

    private fun recuperarLlaveUa(): LlaveUA {
        val sesion = requireNotNull(sesionManager.get())

        return LlaveUA(
            codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
            codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
            codigoSeccion = sesion.seccion.aGuionSiEsNullOVacio(),
            consultoraId = null
        )
    }


    private fun generarResponsePropio(): Response {
        return Response(
            seleccionadorFocos.seleccionables,
            seleccionadorPersonas.seleccionables,
            validadorGuardadoFocos.validarFocosPropios(),
            seleccionSociasHabilitada,
            true
        )
    }

    class Response(
        val focos: List<Seleccionable<Foco>>,
        val personas: List<Seleccionable<PersonaRdd>>,
        val esPosibleGuardar: Boolean,
        val seleccionHabilitada: Boolean,
        val autoAsignacion: Boolean
    )

}
