package biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.digital.core.data.network.DigitalApi
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalDto
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalQuery

class DigitalCloudStore(
    private val api: DigitalApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getDigitalInfo(query: DigitalQuery): DigitalDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getDigitalInfo(request)
        }
        return requireNotNull(response?.data)
    }

}
