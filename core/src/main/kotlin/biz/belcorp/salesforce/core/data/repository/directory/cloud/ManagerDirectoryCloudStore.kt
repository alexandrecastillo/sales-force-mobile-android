package biz.belcorp.salesforce.core.data.repository.directory.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryDto
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryQuery

class ManagerDirectoryCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun getManagerDirectory(query: ManagerDirectoryQuery):ManagerDirectoryDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getManagerDirectory(request)
        }
        return requireNotNull(response?.data)
    }
}