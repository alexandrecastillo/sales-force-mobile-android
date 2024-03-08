package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthNewTokenResponse(
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("detail")
    val detail: Detail
) {

    @Serializable
    data class Detail(
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String
    )

}
