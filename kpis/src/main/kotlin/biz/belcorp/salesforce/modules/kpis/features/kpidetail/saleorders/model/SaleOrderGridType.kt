package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    SaleOrderGridType.SALES,
    SaleOrderGridType.ORDERS,
    SaleOrderGridType.ACTIVITY,
    SaleOrderGridType.ACTIVES_RETENTION,
    SaleOrderGridType.NONE
)
annotation class SaleOrderGridType {
    companion object {
        const val NONE = "-1"
        const val SALES = "key_filter_sales"
        const val ORDERS = "key_filter_orders"
        const val ACTIVITY = "key_filter_activity"
        const val ACTIVES_RETENTION = "key_filter_activites_retention"
    }
}
