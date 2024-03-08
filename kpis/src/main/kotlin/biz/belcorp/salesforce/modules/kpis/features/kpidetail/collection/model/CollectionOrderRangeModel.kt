package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

data class CollectionOrderRangeModel(
    val range: String,
    val collected: Int,
    val total: Int,
    val position: Int
)