package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.mapper

import biz.belcorp.salesforce.core.entities.ranking.RankingEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingDto
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking.RankingGraphic

class ProfileRankingMapper {

    fun map(dto: RankingDto): List<RankingEntity> =
        dto.ranking.map { map(it) }

    private fun map(ranking: RankingDto.Ranking): RankingEntity =
        with(ranking) {
            return RankingEntity(
                campaign = campaign,
                region = region,
                zone = zone,
                productivityPercentage = productivityPercentage,
                uniqueIpPercentage = uniqueIpPercentage,
                retention6d6Percentage = retention6d6Percentage,
                activesGoalRetentionPercentage = activesRetentionGoalPercentage
            )
        }

    fun map(
        rankingInfo: List<RankingEntity>,
        salesInfo: List<SaleOrdersEntity>
    ): List<RankingGraphic> {
        return rankingInfo.map { ranking ->
            val sales = salesInfo.find { it.campaign == ranking.campaign }
            map(ranking, sales ?: SaleOrdersEntity())
        }
    }

    private fun map(ranking: RankingEntity, sales: SaleOrdersEntity): RankingGraphic {
        with(ranking) {
            return RankingGraphic(
                campaign = campaign,
                netSale = sales.netSale.toFloat(),
                netSaleGoal = sales.netSaleGoal.toFloat(),
                netSalePercentage = sales.fulfillmentNetSalePercentage.toFloat(),
                productivityPercentage = productivityPercentage.toFloat(),
                uniqueIpPercentage = uniqueIpPercentage.toFloat(),
                retention6d6Percentage = retention6d6Percentage.toFloat(),
                retentionActivesPercentage = activesGoalRetentionPercentage.toFloat()
            )
        }
    }

}
