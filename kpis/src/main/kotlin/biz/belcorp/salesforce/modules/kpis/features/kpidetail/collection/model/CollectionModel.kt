package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

import biz.belcorp.salesforce.core.constants.Constant

data class CollectionModel(
    val campaign: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val percentage: Double = Constant.EMPTY_DOUBLE,
    val ordersTotalGained: Double = Constant.EMPTY_DOUBLE,
    val ordersMinimalCollectionPercentage: Double = Constant.EMPTY_DOUBLE,
    val ordersTotalCollected: Double = Constant.EMPTY_DOUBLE,
    val ordersTotal: Double = Constant.EMPTY_DOUBLE,
    val ordersRange: List<CollectionOrderRangeModel> = arrayListOf())
