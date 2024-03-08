package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultantsApi
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantsQuery
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalQuery
import com.google.gson.GsonBuilder

class ConsultantsCloudStore(
    private val api: ConsultantsApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {
        private const val TAG_REQUEST = "SalesForce-ConsultantsAPI => Request"
    }

    suspend fun getConsultants(query: ConsultantsQuery): ConsultantDto {
        val request = query.get().toRequestString()
        Logger.d(TAG_REQUEST, request)
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getConsultants(request)
        }

        Logger.d(TAG_REQUEST, response.toString())

        return requireNotNull(response?.data)
    }

    suspend fun getDigitalConsultants(query: DigitalQuery): DigitalDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getDigitalInfo(request)
        }
        return requireNotNull(response?.data)
    }
}
