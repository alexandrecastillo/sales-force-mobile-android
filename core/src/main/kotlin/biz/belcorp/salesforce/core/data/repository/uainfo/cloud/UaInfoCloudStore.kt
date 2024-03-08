package biz.belcorp.salesforce.core.data.repository.uainfo.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.network.dto.UaResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper

class UaInfoCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getUa(country: String, query: Map<String, String>): List<UaResponse> {
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getUa(country, query)
        }
        return requireNotNull(response)
    }
}
