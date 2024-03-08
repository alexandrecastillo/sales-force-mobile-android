package biz.belcorp.salesforce.core.data.repository.session.cloud


import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.session.cloud.dto.ProfileQuery


class ProfileCloudStore(
    private val belcorpApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getProfile(query: ProfileQuery): ProfileQuery.Data {
        val request = query.get().toRequestString()
        val profileInfo = apiCallHelper.safeBelcorpApiCall {
            belcorpApi.getProfileInfo(request)
        }
        return requireNotNull(profileInfo).data
    }

}
