package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_SAVE_APPROVED_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.TermDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SaveTermsDto(
    @SerialName(KEY_SAVE_APPROVED_TERMS)
    val approveTerm: ItemApprovedTerms?
) {
    @Serializable
    data class ItemApprovedTerms(
        @SerialName(KEY_TERMS)
        val terms: List<TermDto>
    )
}
