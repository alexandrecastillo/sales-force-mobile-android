package biz.belcorp.salesforce.core.data.repository.browser.cloud

import biz.belcorp.salesforce.core.data.network.WsSomosBelcorpApi
import biz.belcorp.salesforce.core.data.repository.browser.cloud.dto.LoginResponseDto
import biz.belcorp.salesforce.core.data.repository.browser.cloud.dto.MyAcademyResponseDto
import biz.belcorp.salesforce.core.utils.safeApiCall


class MyAcademyCloudStore(
    private val somosBelcorpApi: WsSomosBelcorpApi
) {

    suspend fun login(username: String, country: String): LoginResponseDto {
        val response = safeApiCall {
            somosBelcorpApi.login(GRANT_TYPE, username, country, AUTH_TYPE)
        }
        return requireNotNull(response)
    }

    suspend fun getUrl(request: HashMap<String, String?>): MyAcademyResponseDto {
        val response = safeApiCall { somosBelcorpApi.getMyAcademyUrl(request) }
        return requireNotNull(response)
    }

    companion object {

        private const val GRANT_TYPE = "password"
        private const val AUTH_TYPE = "3"

    }

}
