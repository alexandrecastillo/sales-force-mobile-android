package biz.belcorp.salesforce.core.data.repository.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms.ApproveTermsDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionDto
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.SaveTerms
import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions
import biz.belcorp.salesforce.core.entities.politicstermsconditions.ApproveTermsConditionsEntity
import biz.belcorp.salesforce.core.entities.politicstermsconditions.TermsConditionsEntity
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import biz.belcorp.salesforce.core.utils.readString

object TermsConditionMockDataHelper {

    private fun fetchTerms(): TermsConditionDto {
        val jsonString = readString("core/data/termsconditions.json")
        return JsonEncodedDefault.decodeFromString(TermsConditionDto.serializer(), jsonString)
    }

    private fun fetchApproveTerms(): ApproveTermsDto {
        val jsonString = readString("core/data/approveterms.json")
        return JsonEncodedDefault.decodeFromString(ApproveTermsDto.serializer(), jsonString)
    }

    fun getTermsEntities(): List<TermsConditionsEntity> {
        val mapper = TermsConditionsMapper()
        return mapper.mapToTermsEntity(fetchTerms().terms)
    }

    private fun getApproveTermsEntities(): List<ApproveTermsConditionsEntity> {
        val mapper = TermsConditionsMapper()
        return mapper.mapToApprovedTermsEntity(
            fetchApproveTerms().terms?.approvedTerms ?: emptyList()
        )
    }

    fun getApproveTerm(): ApproveTermsConditionsEntity?{
        return getApproveTermsEntities().firstOrNull()
    }

    fun getTerm(): TermsConditionsEntity?{
        return getTermsEntities().firstOrNull()
    }

    fun getTermsConditionsModel(): List<TermsConditions> {
        val mapper = TermsConditionsMapper()
        return mapper.mapTerms(getTermsEntities())
    }


    fun getTermUrl(): String {
        val term = fetchTerms().terms.firstOrNull()
        return term?.url.orEmpty()
    }

    fun isTermApproved(): Boolean {
        val term = fetchApproveTerms().terms?.approvedTerms?.firstOrNull()
        return term?.checked ?: true
    }

    fun isTermBlocked(): Boolean {
        val term = fetchApproveTerms().terms?.approvedTerms?.firstOrNull()
        return term?.active ?: true
    }

    fun getApproveParams(): ApproveTermsParams {
        val terms = listOf(SaveTerms("202019", "LINKSE", checked = true, active = true))
        return ApproveTermsParams(
            "CL",
            "03",
            "0302",
            "D",
            "SE",
            "0246329",
            "321654",
            "8025215CBB",
            terms
        )
    }

}
