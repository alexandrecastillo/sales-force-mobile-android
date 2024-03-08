package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.observaciones

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.observaciones.ObservacionVisitaRepository
import io.reactivex.Observable

class ObservacionesVisitaUseCase(
    private val observacionVisitaRepository: ObservacionVisitaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun listar(suscriber: BaseObserver<List<ObservacionVisita>>) {
        val observable = Observable.create<List<ObservacionVisita>> { emitter ->
            val observaciones = observacionVisitaRepository.obtener()

            emitter.onNext(observaciones)
            emitter.onComplete()
        }

        execute(observable, suscriber)
    }
}
