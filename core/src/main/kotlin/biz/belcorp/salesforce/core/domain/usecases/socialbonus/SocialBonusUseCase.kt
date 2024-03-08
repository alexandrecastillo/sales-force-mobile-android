package biz.belcorp.salesforce.core.domain.usecases.socialbonus

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import biz.belcorp.salesforce.core.domain.repository.socialbonus.SocialBonusRepository

class SocialBonusUseCase (
    private val repository: SocialBonusRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<SocialBonus>>) {
        val list = repository.getSocialBonusList()
        execute(list, observer)
    }

    fun one(observer: BaseSingleObserver<SocialBonus>, codigoTipoBono: String) {
        val single = repository.getSocialBonusSingle(codigoTipoBono)
        execute(single, observer)
    }
}
