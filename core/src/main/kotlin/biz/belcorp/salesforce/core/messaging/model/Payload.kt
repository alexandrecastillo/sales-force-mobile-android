package biz.belcorp.salesforce.core.messaging.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Payload {

    @SerialName("id")
    var id: Long? = null
    @SerialName("titulo")
    var title: String? = null
    @SerialName("mensaje")
    var message: String? = null
    @SerialName("data")
    var data: String? = null

    @Transient
    var subtopic: String? = null

}
