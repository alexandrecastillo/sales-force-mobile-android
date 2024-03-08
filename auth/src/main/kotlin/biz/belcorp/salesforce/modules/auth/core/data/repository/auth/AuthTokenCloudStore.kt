package biz.belcorp.salesforce.modules.auth.core.data.repository.auth

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.core.utils.toDateWithT
import biz.belcorp.salesforce.messaging.core.data.network.MessagingApi
import biz.belcorp.salesforce.modules.auth.BuildConfig.OAUTH_PASSWORD
import biz.belcorp.salesforce.modules.auth.core.data.network.AuthApi
import biz.belcorp.salesforce.modules.auth.core.data.network.LegacyAuthApi
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.Credentials.GRANT_TYPE
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.Credentials.USERNAME
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthNewTokenRequest
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthValidateTokenRequest
import okhttp3.Credentials
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.SecurityQuery


class AuthTokenCloudStore(
    private val api: AuthApi,
    private val messagingApi: MessagingApi,
    private val legacyAuthApi: LegacyAuthApi,
    private val authPreferences: AuthSharedPreferences,
    private val userPreferences: UserSharedPreferences
) : AuthTokenRenewer {

    override suspend fun renewLegacyTokenForced(): Boolean {
        val username = userPreferences.username.orEmpty()
        val pais = userPreferences.codPais.orEmpty()
        val rol = userPreferences.codRol.orEmpty()
        val response = safeApiCall {
            legacyAuthApi.token(
                pais = pais,
                usuario = username,
                rol = rol
            )
        }

        messagingApi.getToken(SecurityQuery(BuildConfig.USER_TOKEN, BuildConfig.PWD_TOKEN)).body()
            ?.apply {
                authPreferences.deviceSBToken = this.token
                authPreferences.deviceSBTokenExpires =
                    this.fechaExpiracion.toDateWithT()?.time ?: NUMBER_ZERO_LONG
            }

        return response?.apply {
            authPreferences.legacyToken = this.accessToken
            authPreferences.legacyRefreshToken = this.refreshToken
        }.isNotNull()
    }

    override suspend fun renewLegacyToken(): Boolean {

        val refreshToken = authPreferences.legacyRefreshToken ?: return false

        val validateTokenRequest = AuthValidateTokenRequest(refreshToken)
        safeApiCall { api.validateToken(validateTokenRequest) }

        val newTokenRequest = AuthNewTokenRequest(refreshToken)
        val newTokenResponse = safeApiCall { api.refreshToken(newTokenRequest) }

        messagingApi.getToken(SecurityQuery(BuildConfig.USER_TOKEN, BuildConfig.PWD_TOKEN)).body()
            ?.apply {
                authPreferences.deviceSBToken = this.token
                authPreferences.deviceSBTokenExpires =
                    this.fechaExpiracion.toDateWithT()?.time ?: NUMBER_ZERO_LONG
            }

        return newTokenResponse?.detail?.apply {
            authPreferences.legacyToken = this.accessToken
            authPreferences.legacyRefreshToken = this.refreshToken
        }.isNotNull()
    }

    override suspend fun renewToken(): Boolean {
        val credentials = Credentials.basic(USERNAME, OAUTH_PASSWORD)
        val tokenResponse = safeApiCall { api.token(credentials, GRANT_TYPE) }

        messagingApi.getToken(SecurityQuery(BuildConfig.USER_TOKEN, BuildConfig.PWD_TOKEN)).body()
            ?.apply {
                authPreferences.deviceSBToken = this.token
                authPreferences.deviceSBTokenExpires =
                    this.fechaExpiracion.toDateWithT()?.time ?: NUMBER_ZERO_LONG
            }

        return tokenResponse?.apply {
            authPreferences.token = accessToken
            authPreferences.tokenExpires = tokenExpires.toDateWithT()?.time ?: NUMBER_ZERO_LONG
        }.isNotNull()
    }

}
