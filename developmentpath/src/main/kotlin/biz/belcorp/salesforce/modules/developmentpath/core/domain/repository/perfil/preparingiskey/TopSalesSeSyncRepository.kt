package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeParams

interface TopSalesSeSyncRepository {
    suspend fun sync(params: TopSalesSeParams)
}
