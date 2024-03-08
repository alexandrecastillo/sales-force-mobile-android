package biz.belcorp.salesforce.modules.digital.features.constants

import androidx.annotation.IntDef
import biz.belcorp.salesforce.core.constants.SourceType

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    DigitalFilterType.NONE,
    DigitalFilterType.SUBSCRIBED,
    DigitalFilterType.BUY,
    DigitalFilterType.SHARE
)
annotation class DigitalFilterType {
    companion object {
        const val NONE = -1
        const val SUBSCRIBED = 0
        const val SHARE = 1
        const val BUY = 2

        fun getSourceType(@DigitalFilterType type: Int): Int {
            return when (type) {
                SUBSCRIBED -> SourceType.DIGITAL_SUBSCRIBED
                SHARE -> SourceType.DIGITAL_SHARE
                BUY -> SourceType.DIGITAL_BUY
                else -> SourceType.NONE
            }
        }
    }
}
