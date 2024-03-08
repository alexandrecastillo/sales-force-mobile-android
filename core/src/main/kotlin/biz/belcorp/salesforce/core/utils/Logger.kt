package biz.belcorp.salesforce.core.utils

import android.util.Log.*
import com.newrelic.agent.android.NewRelic

object Logger {

    private const val LOG_PATTERN = "Priority: %d Tag: %s - Message: %s"

    fun e(throwable: Throwable) =
        recordException(Exception(throwable))

    fun e(tag: String, message: String) =
        log(ERROR, tag, message)

    fun e(tag: String, message: String, exception: Exception) =
        log(ERROR, tag, message, exception)

    fun i(tag: String, message: String) =
        log(INFO, tag, message)

    fun i(tag: String, message: String, exception: Exception) =
        log(INFO, tag, message, exception)

    fun w(tag: String, message: String) =
        log(WARN, tag, message)

    fun w(tag: String, message: String, exception: Exception) =
        log(WARN, tag, message, exception)

    fun v(tag: String, message: String) =
        log(VERBOSE, tag, message)

    fun v(tag: String, message: String, exception: Exception) =
        log(VERBOSE, tag, message, exception)

    fun d(tag: String, message: String) =
        log(DEBUG, tag, message)

    fun d(tag: String, message: String, exception: Exception) =
        log(DEBUG, tag, message, exception)

    private fun log(priority: Int, tag: String, message: String, exception: Exception? = null) {
        if (exception != null) recordException(exception)
        else recordLog(String.format(LOG_PATTERN, priority, tag, message))
    }

    private fun recordLog(message: String) {
        getCrashlyticsInstance()?.log(message)
    }

    private fun recordException(exception: Exception) {
        getCrashlyticsInstance()?.recordException(exception)
        NewRelic.recordHandledException(exception)
    }

    @Deprecated(
        "Use logError", ReplaceWith(
            "e(tag, message.orEmpty())",
            "biz.belcorp.salesforce.core.utils.Logger.e"
        )
    )
    @JvmStatic
    fun loge(tag: String, message: String?) {
        e(tag, message.orEmpty())
    }

    @Deprecated(
        "Use logError", ReplaceWith(
            "e(tag, message.orEmpty(), cause)",
            "biz.belcorp.salesforce.core.utils.Logger.e"
        )
    )
    @JvmStatic
    fun loge(tag: String, message: String?, cause: Exception) {
        e(tag, message.orEmpty(), cause)
    }

    @Deprecated(
        "Use logError", ReplaceWith(
            "e(tag, message.orEmpty(), cause)",
            "biz.belcorp.salesforce.core.utils.Logger.e"
        )
    )
    @JvmStatic
    fun loge(tag: String, message: String?, cause: Throwable) {
        e(tag, message.orEmpty(), cause as? Exception ?: return)
    }

}
