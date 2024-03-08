package biz.belcorp.salesforce.core.constants

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    PeriodType.NONE,
    PeriodType.SALE,
    PeriodType.BILLING
)
annotation class PeriodType {
    companion object {
        const val NONE = -1
        const val SALE = 0
        const val BILLING = 1
    }
}
