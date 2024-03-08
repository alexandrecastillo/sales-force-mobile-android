package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginRequest(
    @SerialName("country")
    val country: String,
    @SerialName("app")
    val app: String = EMPTY_STRING,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String = EMPTY_STRING,
    @SerialName("role")
    val role: String,
    @SerialName("region")
    val region: String? = null,
    @SerialName("zone")
    val zone: String? = null,
    @SerialName("section")
    val section: String? = null
)
