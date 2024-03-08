package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.ValidadorGuardadoHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.DatosPersonaHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class ReconocerHabilidadesUseCase(
    private val hablidadesRepository: HabilidadesRepository,
    private val habilidadesReconoceRepository: HabilidadesReconoceRepository,
    private val campaniasRepository: CampaniasRepository,
    private val datosPersonaHabilidadesRepository: DatosPersonaHabilidadesRepository,
    private val sesionManager: SessionPersonRepository,
    private val sincronizador: SincronizadorReconocerHabilidades,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private lateinit var seleccionadorHabilidades: Seleccionador<Habilidad>
    private lateinit var habilidadesSeleccionadasIds: Array<Long>
    private lateinit var validadorGuardadoHabilidades: ValidadorGuardadoHabilidades
    private lateinit var datosPersonaHabilidades: DatosPersonaHabilidadesRepository.DatosPersonaHabilidades
    private var editarHabilitado = false

    fun recuperarParaVisualizacion(request: RequestVisualizacion) {
        val single = Single.create<Response> { emitter ->

            val habilidades = hablidadesRepository.obtener(request.rol)

            editarHabilitado = false

            seleccionadorHabilidades =
                Seleccionador(habilidades, habilidades.size)

            datosPersonaHabilidades = datosPersonaHabilidadesRepository.obtener(request.personaId,
                request.rol)

            validadorGuardadoHabilidades = ValidadorGuardadoHabilidades(seleccionadorHabilidades)

            habilidadesSeleccionadasIds = habilidadesReconoceRepository.obtener(request.personaId,
                request.campania)

            configurarSeleccionadorHabilidades()

            emitter.onSuccess(obtenerResponse())
        }

        execute(single, request.subscriber)
    }

    fun recuperarParaInsercion(request: RequestInsercion) {
        val single = Single.create<Response> { emitter ->

            val habilidades = hablidadesRepository.obtener(request.rol)

            editarHabilitado = true

            seleccionadorHabilidades = Seleccionador(habilidades, habilidades.size)

            datosPersonaHabilidades = datosPersonaHabilidadesRepository.obtener(request.personaId,
                request.rol)

            validadorGuardadoHabilidades = ValidadorGuardadoHabilidades(seleccionadorHabilidades)

            emitter.onSuccess(obtenerResponse())
        }

        execute(single, request.subscriber)
    }

    fun reconocer(subscriber: BaseCompletableObserver) {

        val llaveUaParaCampania = LlaveUA(
            codigoRegion = datosPersonaHabilidades.codigoRegion,
            codigoZona = datosPersonaHabilidades.codigoZona,
            codigoSeccion = "-",
            consultoraId = null
        )

        val completable = obtenerSesionAsync()
            .doInParallel(campaniasRepository.obtenerCampaniaActualSingle(llaveUaParaCampania))
            .map { armarRequest(it.second, it.first) }
            .flatMapCompletable { habilidadesReconoceRepository.guardar(it) }
            .doAfterTerminate { sincronizador.sincronizar().subscribe({}, {}) }

        execute(completable, subscriber)
    }

    private fun obtenerSesionAsync() = doOnSingle { requireNotNull(sesionManager.get()) }

    private fun armarRequest(campania: Campania,
                             sesion: Sesion
    ): HabilidadesReconoceRepository.Request {
        return HabilidadesReconoceRepository.Request(
            campania = campania.codigo,
            codigoZona = datosPersonaHabilidades.codigoZona,
            codigoRegion = datosPersonaHabilidades.codigoRegion,
            codigoSeccion = null,
            usuarioReconocida = datosPersonaHabilidades.usuario,
            usuarioReconoce = sesion.username,
            habilidades = seleccionadorHabilidades.seleccionados
                .map { it.elemento.id })
    }

    fun cambiarSeleccionHabilidad(posicion: Int, subscriber: BaseSingleObserver<Response>) {
        val single = Single.create<Response> { emitter ->
            seleccionadorHabilidades.invertirSeleccion(posicion)

            emitter.onSuccess(obtenerResponse())
        }

        execute(single, subscriber)
    }

    private fun configurarSeleccionadorHabilidades() {
        seleccionadorHabilidades.seleccionables.forEach {
            if (habilidadesSeleccionadasIds.contains(it.elemento.id)) {
                seleccionadorHabilidades.seleccionar(it)
            }
        }
    }

    private fun obtenerResponse(): Response {
        return Response(habilidades = seleccionadorHabilidades.seleccionables,
            numeroSeleccionadas = seleccionadorHabilidades.seleccionados.size,
            mostrarBotonReconocer = editarHabilitado,
            desactivarSeleccionar = editarHabilitado,
            datosPersonaHabilidades = datosPersonaHabilidades,
            numeroMaximoHabilidades = seleccionadorHabilidades.seleccionables.size)
    }

    class Response(val numeroMaximoHabilidades: Int,
                   val numeroSeleccionadas: Int,
                   val mostrarBotonReconocer: Boolean,
                   val desactivarSeleccionar: Boolean,
                   val habilidades: List<Seleccionable<Habilidad>>,
                   val datosPersonaHabilidades: DatosPersonaHabilidadesRepository.DatosPersonaHabilidades)

    class RequestVisualizacion(val personaId: Long,
                               val rol: Rol,
                               val campania: String,
                               val subscriber: BaseSingleObserver<Response>
    )

    class RequestInsercion(val personaId: Long,
                           val rol: Rol,
                           val subscriber: BaseSingleObserver<Response>
    )

}
