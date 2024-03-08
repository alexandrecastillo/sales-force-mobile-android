package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitQuery

class ProfitCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getProfit(query: ProfitQuery): ProfitDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getProfit(request)
        }
        return requireNotNull(response?.data)
    }
}
