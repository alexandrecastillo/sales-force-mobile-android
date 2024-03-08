package biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.brightpath.core.data.network.BrightPathGraphApi
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelDto
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelQuery

class BusinessPartnerChangeLevelCloudStore(
    private val api: BrightPathGraphApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun getBusinessPartnerChangeLevel(query: BusinessPartnerChangeLevelQuery): BusinessPartnerChangeLevelDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getBusinessPartnerChangeLevel(request)
        }
        return requireNotNull(response).data
    }
}
