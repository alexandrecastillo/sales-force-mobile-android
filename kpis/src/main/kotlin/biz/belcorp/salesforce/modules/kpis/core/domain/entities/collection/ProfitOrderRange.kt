package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

data class ProfitOrderRange(
    val range: String,
    val amount: Double,
    val order: Int
)