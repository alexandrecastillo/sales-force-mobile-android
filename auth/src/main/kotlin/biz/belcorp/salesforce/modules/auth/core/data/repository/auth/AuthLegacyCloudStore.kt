package biz.belcorp.salesforce.modules.auth.core.data.repository.auth

import biz.belcorp.salesforce.core.utils.safeApiCall
import biz.belcorp.salesforce.modules.auth.core.data.network.LegacyAuthApi
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLegacyResponse
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginRequest
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLoginResponse


class AuthLegacyCloudStore(
    private val legacyApi: LegacyAuthApi
) {

    suspend fun token(request: AuthLoginRequest): AuthLoginResponse.Detail {
        val loginResponse = safeApiCall {
            legacyApi.token(
                usuario = request.username,
                pais = request.country,
                rol = request.role
            )
        }
        return map(requireNotNull(loginResponse))
    }

    private fun map(response: AuthLegacyResponse): AuthLoginResponse.Detail {
        return AuthLoginResponse.Detail(
            accessToken = response.accessToken.orEmpty(),
            refreshToken = response.refreshToken.orEmpty()
        )
    }

}
