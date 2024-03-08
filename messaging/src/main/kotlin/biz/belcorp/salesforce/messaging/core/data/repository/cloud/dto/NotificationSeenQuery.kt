package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationSeenQuery(
    @SerialName(NOTIFICATION_ID)
    val code: Long,
    @SerialName(DEVICE_ID)
    val deviceID: Long = 0,
    @SerialName(USER)
    val user: String = Constant.EMPTY_STRING
) {
    companion object {
        const val NOTIFICATION_ID = "notificacionID"
        const val DEVICE_ID = "dispositivoID"
        const val USER = "usuario"
    }
}
