package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.formatShort
import biz.belcorp.salesforce.core.utils.string
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class ConfirmarEventoAtraccionUseCase(
    private val eventoXUaRepository: EventoXUaRepository,
    private val sesionManager: SessionPersonRepository,
    private val rddPlanRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun confirmar(eventoXUaId: Long, observer: BaseCompletableObserver) {
        val completable = eventoXUaRepository.recuperarEventoId(eventoXUaId)
            .flatMapCompletable { registrar(eventoXUaId) }
        execute(completable, observer)
    }

    fun getplanId(): Long? {
        val llaveUa = sesionManager.get()?.llaveUA
        val planId = rddPlanRepository.obtenerInfoPlanRdd(llaveUa!!)
        return planId?.planId
    }

    private fun registrar(eventoXUaId: Long): Completable {
        return registroParams(eventoXUaId)
            .flatMapCompletable { eventoXUaRepository.registrar(it) }
            .doAfterTerminate { enviarEventosXUa() }
    }

    private fun registroParams(eventoXUaId: Long): Single<RegistrarEventoParams> {
        return Single.create {
            val usuario = requireNotNull(sesionManager.get()).codigoUsuario
            val date = Calendar.getInstance().time
            val fechaRegistro = date.string(formatShort).orEmpty()
            it.onSuccess(
                RegistrarEventoParams(
                    eventoXUaId = eventoXUaId,
                    usuarioModificacion = usuario,
                    fechaRegistro = fechaRegistro
                )
            )
        }
    }

    private fun enviarEventosXUa() {
        val completable = eventoXUaRepository.enviarAServidor()
        execute(completable, EnvioSubscriber())
    }

    class RegistrarEventoParams(
        val eventoXUaId: Long,
        val usuarioModificacion: String,
        val fechaRegistro: String
    )

    private class EnvioSubscriber : BaseCompletableObserver() {

        override fun onError(e: Throwable) = e.printStackTrace()
    }
}
