package biz.belcorp.salesforce.messaging.core.domain.entities

import biz.belcorp.salesforce.core.utils.safeJsonParse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class PostulantsNotification : Notification() {

    val data by lazy { safeJsonParse(Data.serializer(), dataString) }

    @Serializable
    class Data {

        @SerialName("newConsultant")
        var newConsultant: Boolean = false
        @SerialName("newPostulant")
        var newPostulant: Boolean = false
        @SerialName("newPostulantToAprove")
        var newPostulantToAprove: Boolean = false
        @SerialName("code")
        var code: String? = null
        @SerialName("state")
        var state: String? = null
        @SerialName("step")
        var step: String? = null

    }

}
