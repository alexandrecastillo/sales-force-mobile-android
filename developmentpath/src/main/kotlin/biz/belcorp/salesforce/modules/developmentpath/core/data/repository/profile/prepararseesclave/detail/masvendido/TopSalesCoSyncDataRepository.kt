package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesCoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers.TopSalesCoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud.TopSalesCoCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoQuery
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesCoSyncRepository

class TopSalesCoSyncDataRepository(
    private val cloudStore: TopSalesCoCloudStore,
    private val dataStore: TopSalesCoDataStore,
    private val mapper: TopSalesCoMapper
) : TopSalesCoSyncRepository {

    override suspend fun sync(params: TopSalesCoParams) {
        if (!dataStore.hasTopSalesConsultants(params.consultantCode, params.campaigns)) {
            val response = cloudStore.getTopSalesConsultant(TopSalesCoQuery(params))
            val entities = mapper.map(response.list)
            dataStore.saveSalesConsultants(entities)
        }
    }


}
