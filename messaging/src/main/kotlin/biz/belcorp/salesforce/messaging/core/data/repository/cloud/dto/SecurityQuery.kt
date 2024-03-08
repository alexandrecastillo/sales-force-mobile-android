package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SecurityQuery(
    @SerialName(NAME)
    val name: String,
    @SerialName(PWD)
    val pwd: String
) {

    companion object {
        const val NAME = "Nombre"
        const val PWD = "Password"
    }

}
