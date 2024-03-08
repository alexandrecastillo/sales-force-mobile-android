package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.mappers

import biz.belcorp.salesforce.core.utils.formatWithNoDecimalThousands
import biz.belcorp.salesforce.core.utils.toHundredPercentage
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking.RankingGraphic
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.NetSaleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.RankingGraphicModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import kotlin.math.roundToInt

class RankingChartsMapper {

    fun map(list: List<RankingGraphic>): RankingGraphicModel {

        val netSaleData = mutableListOf<NetSaleModel>()
        val netSaleChartData = mutableListOf<ChartEntryModel>()
        val activesRetentionChartData = mutableListOf<ChartEntryModel>()
        val productivityChartData = mutableListOf<ChartEntryModel>()
        val uniqueIpChartData = mutableListOf<ChartEntryModel>()
        val retention6d6ChartData = mutableListOf<ChartEntryModel>()

        list.forEach {
            val campaign = it.campaign.maskCampaignWithPrefix()
            netSaleData += NetSaleModel(
                campaign = campaign,
                netSale = it.netSale.formatWithNoDecimalThousands(),
                netSaleGoal = it.netSaleGoal.formatWithNoDecimalThousands(),
                netSalePercentage = it.netSalePercentage.toHundredPercentage().roundToInt().toString(),
                saleStatus = it.netSale > it.netSaleGoal
            )
            netSaleChartData += ChartEntryModel(
                caption = campaign,
                firstValue = it.netSale.toHundredPercentage(),
                secondValue = it.netSaleGoal.toHundredPercentage()
            )
            activesRetentionChartData += ChartEntryModel(
                caption = campaign,
                firstValue = it.retentionActivesPercentage.toHundredPercentage()
            )
            productivityChartData += ChartEntryModel(
                caption = campaign,
                firstValue = it.productivityPercentage.toHundredPercentage()
            )
            uniqueIpChartData += ChartEntryModel(
                caption = campaign,
                firstValue = it.uniqueIpPercentage.toHundredPercentage()
            )
            retention6d6ChartData += ChartEntryModel(
                caption = campaign,
                firstValue = it.retention6d6Percentage.toHundredPercentage()
            )
        }

        return RankingGraphicModel(
            netSale = RankingGraphicModel.NetSale(netSaleData, netSaleChartData),
            activesRetention = RankingGraphicModel.ActivesRetention(activesRetentionChartData),
            productivity = RankingGraphicModel.Productivity(productivityChartData),
            uniqueIp = RankingGraphicModel.UniqueIp(uniqueIpChartData),
            retention6d6 = RankingGraphicModel.Retention6d6(retention6d6ChartData)
        )
    }

}
