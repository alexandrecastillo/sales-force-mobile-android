package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

class SaleCreatorParams(
    val isBright: Boolean = false,
    val isGZ: Boolean = false,
    val currency: String,
    val campaign: String,
    val request: SaleOrdersIndicatorModel
)
