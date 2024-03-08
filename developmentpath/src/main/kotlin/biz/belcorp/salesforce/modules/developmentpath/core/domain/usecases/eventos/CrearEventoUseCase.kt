package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloCreacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Completable
import io.reactivex.Single

class CrearEventoUseCase(
    sesionManager: SessionPersonRepository,
    campaniaRepository: CampaniasRepository,
    eventoRepository: EventoRepository,
    programadorAlarmas: ProgramarAlarmasEventosUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    CambioEventoUseCase(
        eventoRepository = eventoRepository,
        sesionManager = sesionManager,
        campaniaRepository = campaniaRepository,
        programadorAlarmas = programadorAlarmas,
        threadExecutor = threadExecutor,
        postExecutionThread = postExecutionThread
    ) {

    fun crear(modelo: EventoRddModeloCreacion, observer: BaseCompletableObserver) {

        val completable = validarFechas(modelo)
            .andThen(asignarUsuario(modelo))
            .flatMap { asignarCampania(it) }
            .flatMap { asignarUa(it) }
            .flatMap { eventoRepository.crear(modelo) }
            .flatMapCompletable {
                generarEventoXUa(it)
            }
            .andThen(programarAlarmas())
            .doAfterTerminate { enviarAServidor() }

        execute(completable, observer)
    }

    private fun asignarUsuario(modelo: EventoRddModeloCreacion): Single<EventoRddModeloCreacion> {
        return Single.create {
            val usuario = sesionManager.get()?.codigoUsuario
            modelo.usuarioCreacion = usuario

            it.onSuccess(modelo)
        }
    }

    private fun asignarCampania(modelo: EventoRddModeloCreacion): Single<EventoRddModeloCreacion> {
        return Single.create {
            val campania =
                requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())?.codigo)
            modelo.campania = campania

            it.onSuccess(modelo)
        }
    }

    private fun asignarUa(modelo: EventoRddModeloCreacion): Single<EventoRddModeloCreacion> {
        return Single.create {
            modelo.llaveUa = recuperarLlaveUa()

            it.onSuccess(modelo)
        }
    }

    private fun generarEventoXUa(eventoId: Long): Completable {
        return armarParam(eventoId).flatMapCompletable { eventoRepository.crear(it) }
    }

    private fun armarParam(eventoId: Long): Single<GenerarEventoXUaParam> {
        return Single.create {

            val llaveUa = recuperarLlaveUa()
            val param = GenerarEventoXUaParam(llaveUA = llaveUa, idEvento = eventoId)

            it.onSuccess(param)
        }
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

    class GenerarEventoXUaParam(
        val llaveUA: LlaveUA,
        val idEvento: Long
    )
}
