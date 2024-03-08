package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid

import androidx.annotation.IntDef


@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    KpiGridViewType.KPI_GRID_ITEM_1,
    KpiGridViewType.KPI_GRID_ITEM_2,
    KpiGridViewType.KPI_GRID_ITEM_3,
    KpiGridViewType.KPI_GRID_ITEM_4,
    KpiGridViewType.KPI_GRID_ITEM_5
)

annotation class KpiGridViewType {
    companion object {
        const val KPI_GRID_ITEM_1 = 61
        const val KPI_GRID_ITEM_2 = 62
        const val KPI_GRID_ITEM_3 = 63
        const val KPI_GRID_ITEM_4 = 64
        const val KPI_GRID_ITEM_5 = 65
    }
}
