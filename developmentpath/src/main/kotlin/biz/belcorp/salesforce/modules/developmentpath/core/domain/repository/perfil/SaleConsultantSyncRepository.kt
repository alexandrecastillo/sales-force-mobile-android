package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantParams

interface SaleConsultantSyncRepository {
    suspend fun sync(params: SaleConsultantParams)
}
