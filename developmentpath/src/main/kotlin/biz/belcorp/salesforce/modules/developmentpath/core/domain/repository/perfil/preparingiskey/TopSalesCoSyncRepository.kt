package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoParams

interface TopSalesCoSyncRepository {
    suspend fun sync(params: TopSalesCoParams)
}
