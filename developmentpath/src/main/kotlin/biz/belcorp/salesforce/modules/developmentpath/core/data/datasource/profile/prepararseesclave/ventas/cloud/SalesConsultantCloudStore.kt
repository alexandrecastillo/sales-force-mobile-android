package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantQuery

class SalesConsultantCloudStore(
    private val profileInfoApi: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun getSaleConsultants(query: SaleConsultantQuery): SaleConsultantDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.getSaleConsultants(request)
        }
        return requireNotNull(response).data
    }
}
