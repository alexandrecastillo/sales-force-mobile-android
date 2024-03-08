package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto.ProfileProfitDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto.ProfileProfitQuery

class ProfileProfitCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getProfileProfit(query: ProfileProfitQuery): ProfileProfitDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getProfileProfit(request)
        }
        return requireNotNull(response?.data)
    }
}
