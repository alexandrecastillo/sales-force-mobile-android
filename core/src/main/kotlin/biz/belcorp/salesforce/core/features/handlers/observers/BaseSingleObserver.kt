package biz.belcorp.salesforce.core.features.handlers.observers

import biz.belcorp.salesforce.core.features.handlers.isUnauthorizedException
import biz.belcorp.salesforce.core.features.handlers.logException
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

open class BaseSingleObserver<T> : SingleObserver<T> {

    override fun onSuccess(t: T) = Unit

    override fun onSubscribe(d: Disposable) = Unit

    override fun onError(exception: Throwable) {
        logException(exception)
        if (exception.isUnauthorizedException()) return
    }
}
