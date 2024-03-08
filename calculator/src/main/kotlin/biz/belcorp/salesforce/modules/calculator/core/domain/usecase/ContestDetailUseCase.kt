package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ContestDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.contestdetail.ContestDetailRepository

class ContestDetailUseCase (
    private val repository: ContestDetailRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<ContestDetail>>) {
        val list = repository.list()
        execute(list, observer)
    }
}
