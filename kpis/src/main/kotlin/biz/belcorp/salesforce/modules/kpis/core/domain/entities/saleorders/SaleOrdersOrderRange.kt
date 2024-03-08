package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

data class SaleOrdersOrderRange(
    var range: String,
    var amount: Int,
    var position: Int
)