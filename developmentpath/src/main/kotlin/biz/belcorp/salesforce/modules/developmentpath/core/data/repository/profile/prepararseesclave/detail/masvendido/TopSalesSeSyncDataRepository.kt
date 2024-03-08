package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesSeDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers.TopSalesSeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud.TopSalesSeCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeQuery
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesSeSyncRepository

class TopSalesSeSyncDataRepository(
    private val cloudStore: TopSalesSeCloudStore,
    private val dataStore: TopSalesSeDataStore,
    private val mapper: TopSalesSeMapper
) : TopSalesSeSyncRepository {

    override suspend fun sync(params: TopSalesSeParams) {
        val query = TopSalesSeQuery(params)
        val response = cloudStore.getTopSalesSe(query)
        val entities = mapper.map(response.list)
        dataStore.save(entities)
    }

}
