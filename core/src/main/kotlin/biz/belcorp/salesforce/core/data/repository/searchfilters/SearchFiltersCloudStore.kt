package biz.belcorp.salesforce.core.data.repository.searchfilters

import biz.belcorp.salesforce.core.data.network.SyncApi
import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.searchfilters.dto.SearchFiltersDataResponse


internal class SearchFiltersCloudStore(
    private val syncApi: SyncApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {
        private const val TAG = "SearchFiltersResponse =>"
    }

    suspend fun sync(ua: String): LegacySyncResponse.Resultado<SearchFiltersDataResponse> {
        val response = apiCallHelper.safeLegacyApiCall { syncApi.searchfilters(ua) }
        return requireNotNull(response?.resultado).also { it.print(TAG) }
    }

}
