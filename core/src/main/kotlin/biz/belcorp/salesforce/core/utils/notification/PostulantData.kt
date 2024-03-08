package biz.belcorp.salesforce.core.utils.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PostulantData {
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
