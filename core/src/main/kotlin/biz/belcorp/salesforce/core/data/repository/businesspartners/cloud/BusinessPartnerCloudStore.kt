package biz.belcorp.salesforce.core.data.repository.businesspartners.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerDto
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerQuery

class BusinessPartnerCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getBusinessPartners(query: BusinessPartnerQuery): BusinessPartnerDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getBusinessPartners(request)
        }
        return requireNotNull(response?.data)
    }
}
