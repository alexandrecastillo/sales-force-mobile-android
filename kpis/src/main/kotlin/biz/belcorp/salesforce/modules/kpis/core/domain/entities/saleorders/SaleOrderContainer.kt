package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

class SaleOrderContainer(
    val saleOrdersIndicator: SaleOrdersIndicator,
    val currencySymbol: String,
    val isBilling: Boolean,
    val isBright: Boolean,
    val isThirdPerson: Boolean = false
)