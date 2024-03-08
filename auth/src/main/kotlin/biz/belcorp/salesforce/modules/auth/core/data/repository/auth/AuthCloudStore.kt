package biz.belcorp.salesforce.modules.auth.core.data.repository.auth

import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.modules.auth.BuildConfig.OAUTH_PASSWORD
import biz.belcorp.salesforce.modules.auth.core.data.network.AuthApi
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.Credentials.GRANT_TYPE
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.Credentials.USERNAME
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginRequest
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginResponse
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthTokenResponse
import okhttp3.Credentials


class AuthCloudStore(private val api: AuthApi) {

    suspend fun login(request: AuthLoginRequest): AuthLoginResponse.Detail {
        val loginResponse = safeApiCall { api.login(request) }
        return requireNotNull(loginResponse).detail
    }

    suspend fun support(request: AuthLoginRequest): AuthLoginResponse.Detail {
        val loginResponse = safeApiCall { api.support(request) }
        return requireNotNull(loginResponse).detail
    }

    suspend fun token(): AuthTokenResponse {
        val credentials = Credentials.basic(USERNAME, OAUTH_PASSWORD)
        val tokenResponse = safeApiCall { api.token(credentials, GRANT_TYPE) }
        return requireNotNull(tokenResponse)
    }

}
