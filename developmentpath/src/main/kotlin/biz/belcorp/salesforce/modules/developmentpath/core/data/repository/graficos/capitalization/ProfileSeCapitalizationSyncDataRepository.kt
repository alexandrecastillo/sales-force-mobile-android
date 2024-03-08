package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.ProfileSeCapitalizationCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto.ProfileCapitalizationQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.data.ProfileCapitalizationDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.mapper.ProfileCapitalizationSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.capitalization.ProfileSeCapitalizationSyncRepository

class ProfileSeCapitalizationSyncDataRepository(
    private val cloudStore: ProfileSeCapitalizationCloudStore,
    private val dataStore: ProfileCapitalizationDataStore,
    private val mapper: ProfileCapitalizationSyncMapper
) : ProfileSeCapitalizationSyncRepository {

    override suspend fun sync(params: KpiRequestParams) {
        val ua = LlaveUA(params.region, params.zone, params.section)
        val requiredCampaigns = filterRequiredCampaigns(ua, params.campaign)
        if (requiredCampaigns.isNotEmpty()) doSync(ua, params)
    }

    private fun filterRequiredCampaigns(
        ua: LlaveUA,
        requiredCampaigns: List<String>
    ): List<String> {
        val storedData = dataStore.getCapitalizationByUaAndCampaign(ua, requiredCampaigns)
        val storedCampaigns = storedData.map { it.campaign }
        return requiredCampaigns.filter { it !in storedCampaigns }
    }

    private suspend fun doSync(ua: LlaveUA, params: KpiRequestParams) {
        val query = ProfileCapitalizationQuery(params)
        val data = cloudStore.getProfileSeCapitalization(query)
        val items = mapper.map(data)
        val nonStoredItems = filterStoredData(ua, items)
        dataStore.save(nonStoredItems)
    }

    private fun filterStoredData(
        ua: LlaveUA,
        items: List<CapitalizationEntity>
    ): List<CapitalizationEntity> {
        val storedCampaigns = dataStore.getStoredDataByUa(ua).map { it.campaign }.toTypedArray()
        return items.filter { it.campaign !in storedCampaigns }
    }

}
