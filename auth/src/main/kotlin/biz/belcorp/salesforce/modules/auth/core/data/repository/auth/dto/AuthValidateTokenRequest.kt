package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthValidateTokenRequest(
    @SerialName("accessToken")
    val accessToken: String
)
