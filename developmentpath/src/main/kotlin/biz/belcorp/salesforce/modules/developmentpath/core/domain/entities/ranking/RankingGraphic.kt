package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking

class RankingGraphic(
    val campaign: String,
    val netSale: Float,
    val netSaleGoal: Float,
    val netSalePercentage: Float,
    val productivityPercentage: Float,
    val uniqueIpPercentage: Float,
    val retention6d6Percentage: Float,
    val retentionActivesPercentage: Float
)

