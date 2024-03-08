package biz.belcorp.salesforce.core.data.repository.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.TermDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionDto
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.SaveTerms
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms.SaveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions
import biz.belcorp.salesforce.core.entities.politicstermsconditions.ApproveTermsConditionsEntity
import biz.belcorp.salesforce.core.entities.politicstermsconditions.TermsConditionsEntity

class TermsConditionsMapper {

    fun mapTerms(list: List<TermsConditionsEntity>): List<TermsConditions> {
        return list.map { map(it) }
    }

    private fun map(item: TermsConditionsEntity) = with(item) {
        TermsConditions(id, termsCode, description, url, active)
    }

    fun mapApproveTerms(list: List<ApproveTermsConditionsEntity>): List<SaveTerms> {
        return list.map { map(it) }
    }

    fun map(item: ApproveTermsConditionsEntity) = with(item) {
        SaveTerms(campaign, termsCode, checked, active)
    }

    fun mapToTermsEntity(list: List<TermsConditionDto.Terms>): List<TermsConditionsEntity> {
        return list.map { map(it) }
    }

    private fun map(item: TermsConditionDto.Terms) = with(item) {
        TermsConditionsEntity(
            termsCode = termCode,
            description = description,
            url = url,
            position = position,
            active = active

        )
    }

    fun mapToApprovedTermsEntity(list: List<TermDto>): List<ApproveTermsConditionsEntity> {
        return list.map { map(it) }
    }

    private fun map(item: TermDto) = with(item) {
        ApproveTermsConditionsEntity(
            campaign = campaign,
            termsCode = termCode,
            checked = checked,
            active = active
        )
    }

    private fun mapToTermsDto(items: List<SaveTerms>): List<TermDto> {
        return items.map { map(it) }
    }

    private fun map(item: SaveTerms) = with(item) {
        TermDto(campaign, termCode, checked)
    }

    fun mapToSaveTerms(item: ApproveTermsParams) = with(item) {
        SaveTermsParams(
            country = countryId,
            region = region,
            zone = zone,
            section = section,
            role = role,
            consultantCode = consultantCode,
            userCode = userCode,
            documentNumber = documentNumber,
            terms = mapToTermsDto(terms)
        )
    }

}
