package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginResponse(
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: Int,
    @SerialName("detail")
    val detail: Detail
) {

    @Serializable
    data class Detail(
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String,
        @SerialName("country")
        val country: String = EMPTY_STRING,
        @SerialName("role")
        val role: String = EMPTY_STRING,
        @SerialName("name")
        val username: String = EMPTY_STRING
    )

}
