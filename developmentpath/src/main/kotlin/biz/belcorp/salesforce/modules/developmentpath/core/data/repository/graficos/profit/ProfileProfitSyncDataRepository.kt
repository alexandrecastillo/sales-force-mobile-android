package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.ProfileProfitCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto.ProfileProfitQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.data.ProfileProfitDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.mapper.ProfileProfitSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.profit.ProfileProfitSyncRepository

class ProfileProfitSyncDataRepository(
    private val cloudStore: ProfileProfitCloudStore,
    private val dataStore: ProfileProfitDataStore,
    private val mapper: ProfileProfitSyncMapper
) : ProfileProfitSyncRepository {

    override suspend fun sync(params: KpiRequestParams) {
        val uaKey = LlaveUA(params.region, params.zone, params.section)
        val requiredCampaigns = filterRequiredCampaigns(uaKey, params.campaign)
        if (requiredCampaigns.isNotEmpty()) syncData(params)
    }

    private fun filterRequiredCampaigns(
        ua: LlaveUA,
        campaigns: List<String>
    ): List<String> {
        val storedData = dataStore.getDataByUaAndCampaign(ua, campaigns)
        val storedCampaigns = storedData.map { it.campaign }
        return campaigns.filter { it !in storedCampaigns }
    }

    private suspend fun syncData(params: KpiRequestParams) {
        val query = ProfileProfitQuery(params)
        val response = cloudStore.getProfileProfit(query)
        val entities = mapper.map(response)
        dataStore.save(entities)
    }

}
