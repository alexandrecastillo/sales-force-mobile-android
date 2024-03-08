package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.SyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.flatMapOnError
import io.reactivex.Completable
import io.reactivex.Single

class SolicitarEventosCambiadosUseCase(
    private val eventoRepository: EventoRepository,
    private val sesionManager: SessionPersonRepository,
    private val syncRepository: SyncRepository,
    private val campaniaRepository: CampaniasRepository,
    private val programarAlarmasEventosUseCase: ProgramarAlarmasEventosUseCase,
    private val sincronizadorEventos: NotificacionesEventosPresenter,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun solicitar(observer: BaseSingleObserver<EventosCambiadosRespuesta>) {
        val single = solicitarSinObserver()
        execute(single, observer)
    }

    fun solicitarSinObserver(): Single<EventosCambiadosRespuesta> {
        return estaEnProceso()
            .flatMap { enProceso ->
                if (!enProceso)
                    hacerDescargaEventos()
                else
                    emitirNinguno()
            }
            .flatMapOnError { procesarEnError() }
    }

    private fun procesarEnError(): Single<EventosCambiadosRespuesta> {
        return marcarTerminado().andThen(emitirNinguno())
    }

    private fun emitirNinguno() =
        Single.just(EventosCambiadosRespuesta.Ninguno as EventosCambiadosRespuesta)

    private fun hacerDescargaEventos(): Single<EventosCambiadosRespuesta> {
        return marcarProcesando()
            .andThen(armarRequest())
            .flatMap { eventoRepository.obtenerCambios(it) }
            .flatMap { mostrarNotificaciones(it) }
            .flatMap { programarAlarmas(it) }
            .flatMap { marcarTerminado().toSingleDefault(it) }
    }

    private fun programarAlarmas(cambiados: EventosCambiadosRespuesta.Cambiados): Single<EventosCambiadosRespuesta.Cambiados> {
        return programarAlarmasEventosUseCase.programarAlarmas().andThen(Single.just(cambiados))
    }

    private fun mostrarNotificaciones(cambiados: EventosCambiadosRespuesta.Cambiados):
        Single<EventosCambiadosRespuesta.Cambiados> {
        return sincronizadorEventos.mostrarNotificaciones(cambiados).andThen(Single.just(cambiados))
    }

    private fun armarRequest(): Single<Request> {
        return recuperarUa().doInParallel(recuperarFechaSync()).doInParallel(recuperarCampania())
            .map {
                Request(
                    ua = it.first.first,
                    fechaEventosSync = it.first.second,
                    campania = it.second
                )
            }
    }

    private fun recuperarUa(): Single<String> {
        return doOnSingle { requireNotNull(sesionManager.get()?.zonificacion) }
    }

    private fun recuperarCampania(): Single<String> {
        return doOnSingle { requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())?.codigo) }
    }

    private fun recuperarFechaSync(): Single<Long> {
        return doOnSingle { syncRepository.obtenerFechaEventosSync() }
    }

    private fun marcarProcesando(): Completable {
        return syncRepository.marcarSyncEventosEnProceso()
    }

    private fun marcarTerminado(): Completable {
        return syncRepository.marcarSyncEventosTerminado()
    }

    private fun estaEnProceso(): Single<Boolean> {
        return doOnSingle { syncRepository.leerFlagDeProceso() }
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


    class Request(
        val fechaEventosSync: Long,
        val ua: String,
        val campania: String
    )
}
