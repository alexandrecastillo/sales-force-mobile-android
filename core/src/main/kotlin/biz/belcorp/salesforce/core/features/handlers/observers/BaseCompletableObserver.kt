package biz.belcorp.salesforce.core.features.handlers.observers

import biz.belcorp.salesforce.core.features.handlers.isUnauthorizedException
import biz.belcorp.salesforce.core.features.handlers.logException
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

open class BaseCompletableObserver : CompletableObserver {

    override fun onComplete() = Unit

    override fun onError(e: Throwable) {
        logException(e)
        if (e.isUnauthorizedException()) return
    }

    override fun onSubscribe(d: Disposable) = Unit
}
