package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_NAME
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_UNITS
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    @SerialName(KEY_NAME)
    val name: String,
    @SerialName(KEY_UNITS)
    val units: Int
)
