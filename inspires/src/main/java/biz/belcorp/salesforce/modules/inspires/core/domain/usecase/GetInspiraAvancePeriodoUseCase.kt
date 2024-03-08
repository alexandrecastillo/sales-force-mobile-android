package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvancesPeriodo
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.progressperiod.ProgressPeriodRepository

class GetInspiraAvancePeriodoUseCase
constructor(
        private val repository: ProgressPeriodRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<InspiraAvancesPeriodo>>) {
        val list = repository.all()
        execute(list, observer)
    }

    fun has(observer: BaseSingleObserver<Boolean>) {
        val has = repository.has()
        execute(has, observer)
    }
}
