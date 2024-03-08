package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SecurityResponse {
    @SerialName(TOKEN)
    val token: String = ""

    @SerialName(EXPIRATION_DATE)
    val fechaExpiracion: String = ""

    companion object {

        const val TOKEN = "Token"
        const val EXPIRATION_DATE = "FechaExpiracion"

    }
}
