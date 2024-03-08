package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.constants.Constant

data class CollectionIndicator(
    val campaign: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val percentage: Double = Constant.ZERO_DECIMAL,
    var invoicedSale: Double = Constant.ZERO_DECIMAL,
    var amountCollected: Double = Constant.ZERO_DECIMAL,
    var debtorConsultants: Int = Constant.NUMBER_ZERO,
    val ordersTotalGained: Double = Constant.ZERO_DECIMAL,
    val ordersMinimalCollectionPercentage: Double = Constant.ZERO_DECIMAL,
    val ordersTotalCollected: Int = Constant.NUMBER_ZERO,
    val ordersTotal: Int = Constant.NUMBER_ZERO,
    val ordersRange: List<CollectionOrderRange> = emptyList()
)
