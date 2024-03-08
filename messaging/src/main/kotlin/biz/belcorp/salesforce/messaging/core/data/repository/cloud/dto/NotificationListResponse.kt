package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class NotificationListResponse {

    @SerialName("Respuesta")
    val resultado: List<Notification> = emptyList()

    @Serializable
    data class Notification(
        @SerialName("Id")
        val code: Int,
        @SerialName("Titulo")
        val title: String,
        @SerialName("Mensaje")
        val message: String,
        @SerialName("Data")
        var data: String,
        @SerialName("FechaEnvio")
        val date: String,
        @SerialName("Leido")
        val seen: Boolean
    ) {

        @Transient
        var subtopic: String = Constant.EMPTY_STRING

    }

}
