package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NotificationSeenResponse {

    @SerialName("Codigo")
    val code: String? = null
    @SerialName("Mensaje")
    val message: String? = null
    @SerialName("Respuesta")
    val resultado: Boolean = false

}
