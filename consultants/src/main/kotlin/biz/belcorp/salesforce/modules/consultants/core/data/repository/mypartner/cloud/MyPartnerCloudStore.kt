package biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultantsApi
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnerQuery
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto

class MyPartnerCloudStore(
    private val api: ConsultantsApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun getMyPartners(query: MyPartnerQuery): MyPartnersDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getMyPartners(request)
        }
        return requireNotNull(response).data
    }
}
