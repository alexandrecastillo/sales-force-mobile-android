package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_expires")
    val tokenExpires: String
)
