package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.ProfileSeOrdersU6CDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.ProfileSeOrdersU6CCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto.ProfileSeOrdersU6CQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.mapper.ProfileSeOrdersU6CSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.seordersu6c.ProfileSeOrdersU6CSyncRepository

class ProfileSeOrdersU6CSyncDataRepository(
    private val cloudStore: ProfileSeOrdersU6CCloudStore,
    private val dataStore: ProfileSeOrdersU6CDataStore,
    private val mapper: ProfileSeOrdersU6CSyncMapper
) : ProfileSeOrdersU6CSyncRepository {

    override suspend fun sync(params: KpiRequestParams) {
        val ua = LlaveUA(params.region, params.zone, params.section)
        val requiredCampaigns = filterRequiredCapaigns(ua, params.campaign)
        if (requiredCampaigns.isNotEmpty()) doSync(ua, params)
    }

    private fun filterRequiredCapaigns(
        ua: LlaveUA,
        requiredCampaigns: List<String>
    ): List<String> {
        val storedData = dataStore.getOrdersByUaAndCampaign(ua, requiredCampaigns)
        val storedCampaigns = storedData.map { it.campaign }
        return requiredCampaigns.filter { it !in storedCampaigns }
    }

    private suspend fun doSync(ua: LlaveUA, params: KpiRequestParams) {
        val query = ProfileSeOrdersU6CQuery(params)
        val data = cloudStore.getProfileSeOrdersU6C(query)
        val items = mapper.map(data)
        val nonStoredItems = filterStoredData(ua, items)
        dataStore.save(nonStoredItems)
    }

    private fun filterStoredData(
        ua: LlaveUA,
        items: List<SaleOrdersEntity>
    ): List<SaleOrdersEntity> {
        val storedCampaigns = dataStore.getStoredDataByUa(ua).map { it.campaign }.toTypedArray()
        return items.filter { it.campaign !in storedCampaigns }
    }

}
