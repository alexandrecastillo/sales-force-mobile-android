package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_APPROVED_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.TermDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ApproveTermsDto(
    @SerialName(KEY_APPROVED_TERMS)
    val terms: ItemApprovedTermsDto?
) {
    @Serializable
    data class ItemApprovedTermsDto(
        @SerialName(KEY_TERMS)
        val approvedTerms: List<TermDto>
    )
}
