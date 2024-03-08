package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.CatalogSaleConsultant

interface CatalogSaleConsultantRepository {
    suspend fun getCatalogSaleConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<CatalogSaleConsultant>
}
