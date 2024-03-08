package biz.belcorp.salesforce.core.domain.entities.billing

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

class Billing(
    val fulfillmentNetSalesPercentage: Double,
    val fulfillmentCatalogSalesPercentage: Double,
    val fulfillmentOrdersPercentage: Double,
    val currentNetSales: Double,
    val currentCatalogSales: Double,
    val currentOrders: Int,
    val catalogSalesGoal: Double,
    val netSalesGoal: Double,
    val ordersGoal: Int
) {

    val remainingOrders get() = ordersGoal - currentOrders
    val remainingNetSale get() = netSalesGoal - currentNetSales
    val remainingCatalogSale get() = catalogSalesGoal - currentCatalogSales
    var currencySymbol: String = EMPTY_STRING
    var isBright: Boolean = false
    var isThirdPerson: Boolean = false

}
