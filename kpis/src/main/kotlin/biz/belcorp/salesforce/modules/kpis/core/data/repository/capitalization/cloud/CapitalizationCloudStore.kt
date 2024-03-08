package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationQuery

class CapitalizationCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getCapitalization(query: CapitalizationQuery): CapitalizationDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getCapitalization(request)
        }
        return requireNotNull(response?.data)
    }
}
