package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.RetentionDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.RetentionQuery

class RetentionCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun getRetention(query: RetentionQuery): RetentionDto{
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getRetention(request)
        }
        return requireNotNull(response?.data)
    }
}
