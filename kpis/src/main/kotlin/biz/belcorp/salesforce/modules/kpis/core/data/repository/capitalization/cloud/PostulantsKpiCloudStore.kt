package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiQuery

class PostulantsKpiCloudStore(
    private val api: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getPostulantsKpi(query: PostulantsKpiQuery): PostulantsKpiDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getPostulantsKpi(request)
        }
        return requireNotNull(response?.data)
    }

}
