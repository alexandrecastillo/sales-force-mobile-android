package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

data class SaleOrdersSaleRangeModel(
    val range: String,
    val amount: Double,
    val position: Int
)