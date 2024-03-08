package biz.belcorp.salesforce.core.features.handlers

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.utils.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

fun logException(e: Throwable) {
    if (BuildConfig.DEBUG) {
        e.printStackTrace()
    } else {
        Logger.e(e)
    }
}

fun Throwable.isUnauthorizedException(): Boolean {
    return (this is UnauthorizedException).also {
        if (it) LiveDataBus.publish(EventSubject.LOGOUT, ConsumableEvent())
    }
}

class CoroutineExceptionHandler(private val handler: (CoroutineContext, Throwable) -> Unit) :
    AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logException(exception)
        if (exception.isUnauthorizedException()) return
        handler.invoke(context, exception)
    }
}
