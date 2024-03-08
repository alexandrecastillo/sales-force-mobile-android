package biz.belcorp.salesforce.core.constants

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    SourceType.NONE,
    SourceType.KPI_COLLECTION,
    SourceType.KPI_NEW_CYCLES,
    SourceType.KPI_SALE_ORDERS,
    SourceType.KPI_CAPITALIZATION,
    SourceType.BILLING_ADVANCEMENT,
    SourceType.DIGITAL_SUBSCRIBED,
    SourceType.DIGITAL_SHARE,
    SourceType.DIGITAL_BUY
)
annotation class SourceType {
    companion object {
        const val NONE = -1
        const val KPI_COLLECTION = 1
        const val KPI_NEW_CYCLES = 2
        const val KPI_SALE_ORDERS = 3
        const val KPI_CAPITALIZATION = 4
        const val BILLING_ADVANCEMENT = 5
        const val DIGITAL_SUBSCRIBED = 6
        const val DIGITAL_SHARE = 7
        const val DIGITAL_BUY = 8

        fun isKpi(@SourceType type: Int): Boolean {
            return when (type) {
                KPI_COLLECTION,
                KPI_CAPITALIZATION,
                KPI_NEW_CYCLES,
                KPI_SALE_ORDERS -> true
                else -> false
            }
        }

        fun isBillingAdvancement(@SourceType type: Int): Boolean {
            return type == BILLING_ADVANCEMENT
        }

        fun isDigital(@SourceType type: Int): Boolean {
            return when (type) {
                DIGITAL_SUBSCRIBED,
                DIGITAL_BUY,
                DIGITAL_SHARE -> true
                else -> false
            }
        }

    }
}
