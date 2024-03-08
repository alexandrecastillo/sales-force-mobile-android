package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania

data class OrderIndicatorRDD(
    val orders: Int,
    val ordersGoal: Int,
    val fulfillmentsVsOrderObjective: Double
)
