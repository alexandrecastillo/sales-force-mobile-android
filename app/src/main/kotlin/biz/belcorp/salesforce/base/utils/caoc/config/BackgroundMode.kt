package biz.belcorp.salesforce.base.utils.caoc.config

import androidx.annotation.IntDef
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_CRASH
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_SILENT

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    BACKGROUND_MODE_SILENT,
    BACKGROUND_MODE_SHOW_CUSTOM,
    BACKGROUND_MODE_CRASH
)

annotation class BackgroundMode {
    companion object BackgroundMode {
        const val BACKGROUND_MODE_SILENT = 0
        const val BACKGROUND_MODE_SHOW_CUSTOM = 1
        const val BACKGROUND_MODE_CRASH = 2
    }
}
