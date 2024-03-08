package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

data class SaleOrdersSaleRange(
    val range: String,
    val amount: Double,
    val position: Int
)