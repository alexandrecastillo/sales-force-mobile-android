package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.ranking.RankingRepository

class GetInspiraRankingUseCase
constructor(
    private val repository: RankingRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun oneWhenIsUser(subscriber: BaseSingleObserver<InspiraRanking>) {
        val list = repository.oneWhenIsUser()
        execute(list, subscriber)
    }

    fun all(subscriber: BaseSingleObserver<List<InspiraRanking>>) {
        val list = repository.all()
        execute(list, subscriber)
    }
}
