package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto.ProfileSeOrdersU6CDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto.ProfileSeOrdersU6CQuery

class ProfileSeOrdersU6CCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getProfileSeOrdersU6C(query: ProfileSeOrdersU6CQuery): ProfileSeOrdersU6CDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getProfileSeOrdersU6C(request)
        }
        return requireNotNull(response?.data)
    }
}
