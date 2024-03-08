package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto.ProfileCapitalizationDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto.ProfileCapitalizationQuery

class ProfileSeCapitalizationCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getProfileSeCapitalization(query: ProfileCapitalizationQuery): ProfileCapitalizationDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getProfileSeCapitalizationU6C(request)
        }
        return requireNotNull(response?.data)
    }
}
