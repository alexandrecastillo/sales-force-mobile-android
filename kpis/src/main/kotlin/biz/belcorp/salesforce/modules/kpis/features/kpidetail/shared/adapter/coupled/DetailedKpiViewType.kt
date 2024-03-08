package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    DetailedKpiViewType.DETAILED_KPI_ITEM_1,
    DetailedKpiViewType.DETAILED_KPI_ITEM_2,
    DetailedKpiViewType.DETAILED_KPI_ITEM_3
)
annotation class DetailedKpiViewType {
    companion object {
        const val DETAILED_KPI_ITEM_1 = 1
        const val DETAILED_KPI_ITEM_2 = 2
        const val DETAILED_KPI_ITEM_3 = 3
    }
}
