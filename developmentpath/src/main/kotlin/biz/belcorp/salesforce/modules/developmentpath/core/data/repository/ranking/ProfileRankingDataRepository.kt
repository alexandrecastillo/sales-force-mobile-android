package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.ProfileSeOrdersU6CDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.ProfileRankingCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.data.ProfileRankingDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.mapper.ProfileRankingMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking.RankingGraphic
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking.ProfileRankingRepository

class ProfileRankingDataRepository(
    private val rankingCloudStore: ProfileRankingCloudStore,
    private val salesDataStore: ProfileSeOrdersU6CDataStore,
    private val rankingMapper: ProfileRankingMapper,
    private val rankingDataStore: ProfileRankingDataStore
) : ProfileRankingRepository {

    override suspend fun syncRankingInfo(uaKey: LlaveUA, campaigns: List<String>, country: String) {
        val requiredCampaigns = filterRequiredCampaigns(uaKey, campaigns)
        if (requiredCampaigns.isNotEmpty()) syncData(uaKey, requiredCampaigns, country)
    }

    private fun filterRequiredCampaigns(
        ua: LlaveUA,
        campaigns: List<String>
    ): List<String> {
        val storedData = rankingDataStore.getDataByUaAndCampaign(ua, campaigns)
        val storedCampaigns = storedData.map { it.campaign }
        return campaigns.filter { it !in storedCampaigns }
    }

    private suspend fun syncData(uaKey: LlaveUA, campaigns: List<String>, country: String) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val params = RankingParams(country, campaigns, region, zone)
        val query = RankingQuery(params)
        val response = rankingCloudStore.syncRankingInfo(query)
        val entities = rankingMapper.map(response)
        rankingDataStore.save(entities)
    }

    override suspend fun getRankingInfo(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<RankingGraphic> {
        val rankingInfo = rankingDataStore.getDataByUaAndCampaign(uaKey, campaigns)
        val salesInfo = salesDataStore.getOrdersByUaAndCampaign(uaKey, campaigns)
        return rankingMapper.map(rankingInfo, salesInfo)
    }

}
