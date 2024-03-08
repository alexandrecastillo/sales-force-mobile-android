package biz.belcorp.salesforce.core.data.repository.configuration.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto.ConfigurationQuery

class ConfigurationCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getConfiguration(query: ConfigurationQuery): ConfigurationQuery.Data {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getConfiguration(request)
        }
        return requireNotNull(response?.data)
    }
}
