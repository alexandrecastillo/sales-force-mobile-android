package biz.belcorp.salesforce.core.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LegacyErrorBody(
    @SerialName("error")
    val code: String,
    @SerialName("error_description")
    val message: String
)
