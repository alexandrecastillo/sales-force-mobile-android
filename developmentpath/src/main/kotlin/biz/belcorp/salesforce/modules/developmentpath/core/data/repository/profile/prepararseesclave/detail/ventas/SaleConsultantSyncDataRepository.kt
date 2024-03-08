package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas

import android.util.Log
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.cloud.SalesConsultantCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.SaleConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.SaleConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantQuery
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.SaleConsultantSyncRepository

class SaleConsultantSyncDataRepository(
    private val cloudStore: SalesConsultantCloudStore,
    private val dataStore: SaleConsultantDataStore,
    private val mapper: SaleConsultantMapper
) : SaleConsultantSyncRepository {


    override suspend fun sync(params: SaleConsultantParams) = with(params) {
        if (!dataStore.hasConsultantSales(consultantCode, campaigns)) {

            val response = cloudStore.getSaleConsultants(SaleConsultantQuery(this))

            val currentEntities = dataStore.getConsultantSales(consultantCode, campaigns)
            val entities = mapper.map(currentEntities, response.saleConsultants)

            Log.e("TAG", "sync: ")

            dataStore.saveConsultantSales(entities)
        }
    }


}
