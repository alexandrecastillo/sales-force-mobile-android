package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

data class ProfitModel(
    val campaign: String,
    val profile: String,
    val region: String,
    val zone: String,
    val section: String,
    val total: Double,
    val competitionTotal: Double,
    val competitionCapitalization: Double,
    val competition6d6: Double,
    val competitionChangeLevel: Double,
    val competitionNewFixed: Double,
    val competitionProductsRelease: Double,
    val ordersTotal: Double,
    val ordersPotential: Double,
    val ordersRange: List<ProfitOrderRangeModel>
)