package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionQuery

class CollectionCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    companion object {
        private const val TAG_REQUEST = "KPIS-CollectionAPI => Request"
    }

    suspend fun getCollection(query: CollectionQuery): CollectionDto {
        val request = query.get().toRequestString()
        Logger.d(TAG_REQUEST, request)
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getCollection(request)
        }
        return requireNotNull(response?.data)
    }
}
