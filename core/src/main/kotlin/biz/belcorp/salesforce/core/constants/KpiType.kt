package biz.belcorp.salesforce.core.constants

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    KpiType.NONE,
    KpiType.COLLECTION,
    KpiType.NEW_CYCLES,
    KpiType.SALE_ORDERS,
    KpiType.CAPITALIZATION
)
annotation class KpiType {
    companion object {
        const val NONE = -1
        const val COLLECTION = 1
        const val NEW_CYCLES = 2
        const val SALE_ORDERS = 3
        const val CAPITALIZATION = 5

        fun toSourceType(@KpiType kpiType: Int): Int {
            return when (kpiType) {
                COLLECTION -> SourceType.KPI_COLLECTION
                NEW_CYCLES -> SourceType.KPI_NEW_CYCLES
                SALE_ORDERS -> SourceType.KPI_SALE_ORDERS
                CAPITALIZATION -> SourceType.KPI_CAPITALIZATION
                else -> SourceType.NONE
            }
        }
    }
}
