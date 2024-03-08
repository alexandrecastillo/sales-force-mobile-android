package biz.belcorp.salesforce.modules.auth.core.data.repository.auth

import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.WebViewCookieManager
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.MyKeystoreProvider
import biz.belcorp.salesforce.core.utils.toDateWithT
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.NotificationCloudStore
import biz.belcorp.salesforce.modules.auth.core.data.exceptions.CredencialesInvalidasException
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginRequest
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginResponse
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthTokenResponse
import biz.belcorp.salesforce.modules.auth.core.domain.repository.AuthRepository
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.SecurityQuery
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.SecurityResponse


class AuthDataRepository(
    private val authCloudStore: AuthCloudStore,
    private val legacyAuthCloudStore: AuthLegacyCloudStore,
    private val syncPreferences: SyncSharedPreferences,
    private val authPreferences: AuthSharedPreferences,
    private val userPreferences: UserSharedPreferences,
    private val notificationCloudStore: NotificationCloudStore,
    private val cookieManager: WebViewCookieManager
) : AuthRepository {

    override suspend fun login(request: LoginUseCase.Params) {

        syncPreferences.clear()

        val loginRequest = getLoginRequest(request)

        val loginResponse = if (request.ua == null) {
            authCloudStore.login(loginRequest)
        } else {
            authCloudStore.support(loginRequest)
        }

        val tokenResponse = authCloudStore.token()
        val legacyTokenResponse = legacyAuthCloudStore.token(getLegacyTokenRequest(loginResponse))
        val sbTokenResponse = notificationCloudStore.getToken(
            query = SecurityQuery(
                BuildConfig.USER_TOKEN,
                BuildConfig.PWD_TOKEN
            )
        )

        syncPreferences.baseSyncDate = 0

        saveUserData(loginResponse)
        saveAuthData(legacyTokenResponse, tokenResponse, request, sbTokenResponse)

    }

    private fun getLoginRequest(request: LoginUseCase.Params): AuthLoginRequest {
        return AuthLoginRequest(
            country = request.country,
            app = APP,
            username = request.user,
            password = request.password,
            role = getRole(request.appId),
            region = request.ua?.codigoRegion?.takeIf { it.isNotBlank() },
            zone = request.ua?.codigoZona?.takeIf { it.isNotBlank() },
            section = request.ua?.codigoSeccion?.takeIf { it.isNotBlank() }
        )
    }

    private fun getLegacyTokenRequest(response: AuthLoginResponse.Detail): AuthLoginRequest {
        return AuthLoginRequest(
            country = response.country,
            username = response.username,
            role = response.role
        )
    }

    private fun getRole(appId: String): String {
        return if (appId == MULTIPROFILE) {
            Rol.GERENTE_ZONA.codigoRol
        } else {
            Rol.SOCIA_EMPRESARIA.codigoRol
        }
    }

    private fun saveUserData(loginResponse: AuthLoginResponse.Detail) {
        userPreferences.clear()
        userPreferences.codRol = loginResponse.role
        userPreferences.codPais = loginResponse.country
        userPreferences.username = loginResponse.username
    }

    private fun saveAuthData(
        legacyTokenResponse: AuthLoginResponse.Detail,
        tokenResponse: AuthTokenResponse,
        params: LoginUseCase.Params,
        securityResponse: SecurityResponse
    ) {
        authPreferences.clear()
        authPreferences.legacyToken = legacyTokenResponse.accessToken
        authPreferences.legacyRefreshToken = legacyTokenResponse.refreshToken
        authPreferences.token = tokenResponse.accessToken
        authPreferences.tokenExpires = tokenResponse.tokenExpires.toDateWithT()?.time ?: 0L
        authPreferences.username = params.user
        authPreferences.password = params.getSafePassword()
        authPreferences.logged = true
        authPreferences.loginInProgress = true
        authPreferences.deviceSBToken = securityResponse.token
        authPreferences.deviceSBTokenExpires =
            securityResponse.fechaExpiracion.toDateWithT()?.time ?: Constant.NUMBER_ZERO_LONG
    }

    private fun LoginUseCase.Params.getSafePassword(): String {
        return MyKeystoreProvider().encrypt(password, MyKeystoreProvider.PHARSEENCRYPTER)
    }

    override fun validateCredentials(request: LoginUseCase.Params) {
        if (request.user.equals(authPreferences.username, true)) {
            if (request.getSafePassword().trim() != authPreferences.password?.trim()) {
                throw CredencialesInvalidasException()
            } else {
                authPreferences.logged = true
            }
        } else {
            throw NetworkConnectionException()
        }
    }

    override fun logout() {
        cookieManager.clearCookies()
        authPreferences.logged = false
        syncPreferences.clear()
    }

    companion object {

        const val MULTIPROFILE = "2"
        const val APP = "ffvv"

    }

}
