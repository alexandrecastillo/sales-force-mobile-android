package biz.belcorp.salesforce.modules.billing.core.domain.entities

class NewCycleBilling(
    var highValueOrders6d6: Int,
    var highValueOrders5d5: Int,
    var highValueOrders4d4: Int,
    var highValueOrders3d3: Int,
    var lowValueOrders6d6: Int,
    var lowValueOrders5d5: Int,
    var lowValueOrders4d4: Int,
    var lowValueOrders3d3: Int,
    var lowValueOrders2d2: Int,
    var lowValueOrders1d1: Int
)
