package biz.belcorp.salesforce.base.core.data.repository.options.cloud

import biz.belcorp.salesforce.base.core.data.network.BelcorpApi
import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper

class OptionsCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getOptions(query: OptionsQuery): OptionsQuery.Data {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getOptionsMenu(request)
        }
        return requireNotNull(response?.data)
    }

}
