package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

data class ProfitOrderRangeModel(
    val range: String,
    val amount: Double,
    val order: Int
)