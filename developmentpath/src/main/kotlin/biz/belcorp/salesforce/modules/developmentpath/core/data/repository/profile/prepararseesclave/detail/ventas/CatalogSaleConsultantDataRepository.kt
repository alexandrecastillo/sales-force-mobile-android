package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.CatalogSaleConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.CatalogSaleConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.CatalogSaleConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.CatalogSaleConsultantRepository

class CatalogSaleConsultantDataRepository(
    private val dataStore: CatalogSaleConsultantDataStore,
    private val mapper: CatalogSaleConsultantMapper
) : CatalogSaleConsultantRepository {

    override suspend fun getCatalogSaleConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<CatalogSaleConsultant> {
        val (sales, consultant) = dataStore.getCatalogSale(consultantCode, campaigns)
        return mapper.map(sales, consultant)
    }
}
