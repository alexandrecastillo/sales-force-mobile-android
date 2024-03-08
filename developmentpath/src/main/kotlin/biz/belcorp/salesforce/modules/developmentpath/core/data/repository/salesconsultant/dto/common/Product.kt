package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName(KEY_TOP)
    val top: Int,
    @SerialName(KEY_NAME)
    val name: String,
    @SerialName(KEY_AVERAGE_UNITS)
    val units: Double,
    @SerialName(KEY_HISTORY)
    val histories: List<Historical>
) {
    @Serializable
    data class Historical(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_UNITS)
        val units: Int
    )
}


