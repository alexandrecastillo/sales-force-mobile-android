package biz.belcorp.salesforce.messaging.core.domain.entities

import biz.belcorp.salesforce.core.utils.safeJsonParse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class NewsNotification : Notification() {

    val data by lazy { safeJsonParse(Data.serializer(), dataString) }

    @Serializable
    class Data {

        @SerialName("Imagen")
        var image: String? = null
        @SerialName("UrlVideo")
        var videoUrl: String? = null
        @SerialName("ColorBotonX")
        var closeButtonColor: String? = null
        @SerialName("ColorBotonFondo")
        var buttonBackgroundColor: String? = null
        @SerialName("ActionPantalla")
        var action: String? = null

    }

}
