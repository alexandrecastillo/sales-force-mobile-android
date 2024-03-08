package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Completable
import io.reactivex.Single

class EliminarEventoUseCase(
    private val eventoRepository: EventoRepository,
    private val eventoXUaRepository: EventoXUaRepository,
    private val esCreadorUseCase: RecuperarRelacionObservadorEventoUseCase,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun eliminar(eventoXUaId: Long, observer: BaseCompletableObserver) {
        val completable = esCreadorUseCase.determinarSiEsOrganizador(eventoXUaId)
            .flatMapCompletable { determinarEliminarORechazar(eventoXUaId, it) }
        execute(completable, observer)
    }

    private fun determinarEliminarORechazar(eventoXUaId: Long, esCreador: Boolean): Completable {
        return if (esCreador)
            eliminarEvento(eventoXUaId)
        else
            rechazarAsistenciaAEvento(eventoXUaId)
    }

    private fun eliminarEvento(eventoXUaId: Long): Completable {
        return eventoXUaRepository.recuperarEventoId(eventoXUaId)
            .flatMapCompletable { eventoRepository.eliminar(it) }
            .doAfterTerminate { enviarEventos() }
    }

    private fun rechazarAsistenciaAEvento(eventoXUaId: Long): Completable {
        return armarParams(eventoXUaId)
            .flatMapCompletable { eventoXUaRepository.rechazar(it) }
            .doAfterTerminate { enviarEventosXUa() }
    }

    private fun enviarEventos() {
        val completable = eventoRepository.enviarAServidor()
        execute(completable, EnvioSubscriber())
    }

    private fun enviarEventosXUa() {
        val completable = eventoXUaRepository.enviarAServidor()
        execute(completable, EnvioSubscriber())
    }

    private fun armarParams(eventoXUaId: Long): Single<RechazarEventoParams> {
        return Single.create {
            val usuario = requireNotNull(sesionManager.get()).codigoUsuario

            it.onSuccess(
                RechazarEventoParams(
                    eventoXUaId = eventoXUaId,
                    usuarioModificacion = usuario
                )
            )
        }
    }

    class RechazarEventoParams(
        val eventoXUaId: Long,
        val usuarioModificacion: String
    )

    private class EnvioSubscriber : BaseCompletableObserver() {

        override fun onError(e: Throwable) = e.printStackTrace()
    }
}
