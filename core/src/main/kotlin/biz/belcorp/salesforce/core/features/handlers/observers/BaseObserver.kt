package biz.belcorp.salesforce.core.features.handlers.observers


import biz.belcorp.salesforce.core.features.handlers.isUnauthorizedException
import biz.belcorp.salesforce.core.features.handlers.logException
import io.reactivex.observers.DisposableObserver


open class BaseObserver<T> : DisposableObserver<T>() {

    override fun onNext(t: T) = Unit

    override fun onComplete() = Unit

    override fun onError(exception: Throwable) {
        logException(exception)
        if (exception.isUnauthorizedException()) return
    }
}
