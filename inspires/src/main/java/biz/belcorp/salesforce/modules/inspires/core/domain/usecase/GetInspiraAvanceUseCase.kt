package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvances
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.advances.AdvancesRepository

class GetInspiraAvanceUseCase
constructor(
        private val repository: AdvancesRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<InspiraAvances>>) {
        val list = repository.all()
        execute(list, observer)
    }
}
