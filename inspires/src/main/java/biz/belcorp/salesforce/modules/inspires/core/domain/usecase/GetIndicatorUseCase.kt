package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.indicator.IndicatorRepository

class GetIndicatorUseCase (
    private val repository: IndicatorRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun one(observer: BaseSingleObserver<InspiraIndicador>) {
        val single = repository.one()
        execute(single, observer)
    }

}
