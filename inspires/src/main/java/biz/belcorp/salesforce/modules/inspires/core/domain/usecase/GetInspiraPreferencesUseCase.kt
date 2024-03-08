package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.preference.InspiraPreferencesRepository

class GetInspiraPreferencesUseCase
constructor(private val repository: InspiraPreferencesRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread) : UseCase(threadExecutor, postExecutionThread) {

    fun getInspiraAttemp(subscriber: BaseSingleObserver<Int>) {
        val observable = repository.getInspiraAttemp()
        execute(observable, subscriber)
    }

    fun setInspiraAttemp(attemp: Int, subscriber: BaseCompletableObserver) {
        val observer = repository.setInspiraAttemp(attemp)
        execute(observer, subscriber)
    }

    fun getInspiraShowPopup(subscriber: BaseSingleObserver<Boolean>) {
        val observer = repository.getInspiraShowPopup()
        execute(observer, subscriber)
    }

    fun setInspiraShowPopup(show: Boolean, subscriber: BaseCompletableObserver) {
        val observer = repository.setInspiraShowPopup(show)
        execute(observer, subscriber)
    }
}
