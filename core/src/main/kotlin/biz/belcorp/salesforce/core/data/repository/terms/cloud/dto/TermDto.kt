package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_ACTIVE
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_CHECKED
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_TERM_CODE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TermDto(
    @SerialName(KEY_CAMPAIGN)
    val campaign: String,
    @SerialName(KEY_TERM_CODE)
    val termCode: String,
    @SerialName(KEY_CHECKED)
    val checked: Boolean,
    @SerialName(KEY_ACTIVE)
    val active: Boolean = false
)
