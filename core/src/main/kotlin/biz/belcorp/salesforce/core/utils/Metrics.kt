package biz.belcorp.salesforce.core.utils

import android.util.Log
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import com.newrelic.agent.android.NewRelic
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS

object Metrics {

    private const val TAG = "Metrics - NewRelic"

    const val CATEGORY_DBSIZE = "dataBase"
    const val CATEGORY_LOGIN = "login"

    const val OBJECTBOX_DBSIZE = "objectBox_mb"
    const val LOGIN_TIME_SECONDS = "time_seconds"

    fun measure(name: String, category: String, value: Double) {
        try {
            val categoryWithFlavor = "${BuildConfig.FLAVOR}$HYPHEN$category"
            NewRelic.recordMetric(name, categoryWithFlavor, value)
            Log.d(TAG, "measuring (name: $name, category: $categoryWithFlavor, value: $value)")
        } catch (ex: Exception) {
            Logger.e(ex)
        }
    }

    inline fun <T> measureSeconds(name: String, category: String, function: () -> T): T {

        val startTime = System.nanoTime()
        val result: T = function.invoke()
        val endTime = System.nanoTime()

        val seconds = SECONDS.convert(endTime - startTime, NANOSECONDS)

        measure(name, category, seconds.toDouble())

        return result
    }

}
