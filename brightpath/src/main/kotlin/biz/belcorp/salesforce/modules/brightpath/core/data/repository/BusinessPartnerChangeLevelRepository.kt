package biz.belcorp.salesforce.modules.brightpath.core.data.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.modules.brightpath.core.data.datasource.BusinessPartnerChangeLevelDataStore
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.BusinessPartnerChangeLevelMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelParams
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelQuery
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.BusinessPartnerChangeLevelCloudStore
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.BusinessPartnerChangeLevelSyncRepository

class BusinessPartnerChangeLevelRepository(
    private val cloudStore: BusinessPartnerChangeLevelCloudStore,
    private val dataStore: BusinessPartnerChangeLevelDataStore,
    private val mapper: BusinessPartnerChangeLevelMapper
) : BusinessPartnerChangeLevelSyncRepository {

    override suspend fun sync(params: BusinessPartnerChangeLevelParams) {

        val response = cloudStore.getBusinessPartnerChangeLevel(BusinessPartnerChangeLevelQuery(params))
        val entities = mapper.map(response)
        dataStore.saveChangeLevel(entities)
    }

    override fun getBusinessPartnerLevelData(uaKey: LlaveUA): MutableList<BusinessPartnerChangeLevelEntity> = dataStore.getChangeLevelMap(uaKey)


}
