package biz.belcorp.salesforce.core.data.repository.campania

import biz.belcorp.salesforce.core.data.network.SyncApi
import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.campania.dto.CampaniaDataResponse

class CampaniasCloudStore(
    private val syncApi: SyncApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {
        private const val TAG = "CampaniasResponse =>"
    }

    suspend fun sync(ua: String): LegacySyncResponse.Resultado<CampaniaDataResponse> {
        val response = apiCallHelper.safeLegacyApiCall { syncApi.campaigns(ua) }
        return requireNotNull(response?.resultado).also { it.print(TAG) }
    }

}
