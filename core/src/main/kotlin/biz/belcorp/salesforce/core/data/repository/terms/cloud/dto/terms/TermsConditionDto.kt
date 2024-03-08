package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_ACTIVE
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_DESCRIPTION
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_POSITION
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_URL
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_LEGAL_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_TERM_CODE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TermsConditionDto(
    @SerialName(KEY_LEGAL_TERMS)
    val terms: List<Terms>
) {
    @Serializable
    data class Terms(
        @SerialName(KEY_TERM_CODE)
        val termCode: String,
        @SerialName(KEY_DESCRIPTION)
        val description: String,
        @SerialName(KEY_URL)
        val url: String,
        @SerialName(KEY_POSITION)
        val position: Int,
        @SerialName(KEY_ACTIVE)
        val active: Boolean
    )
}
