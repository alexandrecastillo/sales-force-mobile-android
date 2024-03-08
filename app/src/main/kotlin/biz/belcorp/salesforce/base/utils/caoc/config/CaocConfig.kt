package biz.belcorp.salesforce.base.utils.caoc.config

import android.app.Activity
import biz.belcorp.salesforce.base.utils.caoc.Caoc
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM
import biz.belcorp.salesforce.base.utils.caoc.listeners.CaocEventListener
import biz.belcorp.salesforce.base.utils.caoc.values.CaocMessages.EXCEPTION_EVENT_LISTENER
import biz.belcorp.salesforce.core.utils.isNotNull
import java.io.Serializable
import java.lang.reflect.Modifier

class CaocConfig(
    var backgroundMode: Int = BACKGROUND_MODE_SHOW_CUSTOM,
    var enabled: Boolean = true,
    var showErrorDetails: Boolean = true,
    var showRestartButton: Boolean = true,
    var logErrorOnRestart: Boolean = true,
    var trackActivities: Boolean = false,
    var minTimeBetweenCrashesMs: Int = 3000,
    var errorDrawable: Int? = null,
    var errorActivityClass: Class<out Activity>? = null,
    var restartActivityClass: Class<out Activity>? = null,
    var caocEventListener: CaocEventListener? = null
) : Serializable {

    fun clone(): CaocConfig {
        return CaocConfig(
            backgroundMode = backgroundMode,
            enabled = enabled,
            showErrorDetails = showErrorDetails,
            showRestartButton = showRestartButton,
            logErrorOnRestart = logErrorOnRestart,
            trackActivities = trackActivities,
            minTimeBetweenCrashesMs = minTimeBetweenCrashesMs,
            errorDrawable = errorDrawable,
            errorActivityClass = errorActivityClass,
            restartActivityClass = restartActivityClass,
            caocEventListener = caocEventListener
        )
    }

    object Builder {

        private lateinit var config: CaocConfig

        fun create(): Builder {
            config = Caoc.config.clone()
            return this
        }

        fun apply() {
            Caoc.config = config
        }

        fun backgroundMode(@BackgroundMode backgroundMode: Int) =
            apply { config.backgroundMode = backgroundMode }

        fun enabled(enabled: Boolean) =
            apply { config.enabled = enabled }

        fun showErrorDetails(showErrorDetails: Boolean) =
            apply { config.showErrorDetails = showErrorDetails }

        fun showRestartButton(showRestartButton: Boolean) =
            apply { config.showRestartButton = showRestartButton }

        fun logErrorOnRestart(logErrorOnRestart: Boolean) =
            apply { config.logErrorOnRestart = logErrorOnRestart }

        fun trackActivities(trackActivities: Boolean) =
            apply { config.trackActivities = trackActivities }

        fun minTimeBetweenCrashesMs(minTimeBetweenCrashesMs: Int) =
            apply { config.minTimeBetweenCrashesMs = minTimeBetweenCrashesMs }

        fun errorDrawable(errorDrawable: Int) =
            apply { config.errorDrawable = errorDrawable }

        fun errorActivity(errorActivity: Class<out Activity>) =
            apply { config.errorActivityClass = errorActivity }

        fun restartActivity(restartActivity: Class<out Activity>) =
            apply { config.restartActivityClass = restartActivity }

        fun eventListener(caocEventListener: CaocEventListener) =
            apply {
                with(caocEventListener.javaClass) {
                    require(
                        enclosingClass.isNotNull() && !Modifier.isStatic(modifiers)
                    ) { EXCEPTION_EVENT_LISTENER }
                }
                config.caocEventListener = caocEventListener
            }

    }
}
