package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

data class SaleOrdersIndicatorModel(
    val campaign: String,
    val region: String,
    val zone: String,
    val section: String,
    val netSale: Double,
    val netSaleGoal: Double,
    val fulfillmentNetSalePercentage: Double,
    val catalogSale: Double,
    val catalogSaleGoal: Double,
    val fulfillmentCatalogSalesPercentage: Double,
    val orders: Int,
    val ordersGoal: Int,
    val highValueOrders: Int,
    val lowValueOrders: Int,
    val averageAmount: Double,
    val fulfillmentOrderPercentage: Double,
    val fulfillmentOrderAveragePercentage: Double,
    val activityPercentage: Double,
    val activityGoal: Double,
    val activityPegs: Int,
    val fulfillmentActivityPercentage: Double,
    val activesInitials: Int,
    val activesFinals: Int,
    val activesFinalsLastYear: Int,
    val activesRetentionPercentage: Double,
    val activesRetentionGoal: Double,
    val ordersRange: List<SaleOrdersOrderRangeModel>,
    val salesRange: List<SaleOrdersSaleRangeModel>
)
