package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

data class SaleOrdersOrderRangeModel(
    var range: String,
    var amount: Int,
    var position: Int
)