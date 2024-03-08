package biz.belcorp.salesforce.core.data.repository.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.TermsConditionsCloudStore
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms.ApproveTermsQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms.ApproveRequestParams
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms.SaveTermsQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionsQuery
import biz.belcorp.salesforce.core.data.repository.terms.data.TermsConditionsDataStore
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsRequestParams
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.SaveTerms
import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import biz.belcorp.salesforce.core.utils.isNotNull

class TermsConditionsDataRepository(
    private val termsConditionsDataStore: TermsConditionsDataStore,
    private val termsConditionsCloudStore: TermsConditionsCloudStore,
    private val termsConditionsMapper: TermsConditionsMapper
) : TermsConditionsRepository {

    override suspend fun syncTermsConditions(countryId: String, role: String) {
        val params = TermsRequestParams(countryId, role)
        val query = TermsConditionsQuery(params)
        val data = termsConditionsCloudStore.downloadTermsConditions(query)
        val terms = termsConditionsMapper.mapToTermsEntity(data)
        termsConditionsDataStore.saveTermsConditions(terms)
    }

    override suspend fun syncApprovedTermsConditions(
        countryId: String, role: String, code: String,typeQuery: String
    ) {
        val params = ApproveRequestParams(countryId, role, code,typeQuery)
        val query = ApproveTermsQuery(params)
        val data = termsConditionsCloudStore.downloadApproveTerms(query)
        val approvedTerms = termsConditionsMapper.mapToApprovedTermsEntity(data)
        termsConditionsDataStore.saveApproveTermsConditions(approvedTerms)
    }

    override suspend fun saveApprovedTerm(params: ApproveTermsParams): Boolean {
        val query = SaveTermsQuery(termsConditionsMapper.mapToSaveTerms(params))
        val data = termsConditionsCloudStore.saveApproveTerms(query)
        val approvedTerms = termsConditionsMapper.mapToApprovedTermsEntity(data)
        termsConditionsDataStore.saveApproveTermsConditions(approvedTerms)
        return true
    }

    override suspend fun getTermsConditions(): List<TermsConditions> {
        val entities = termsConditionsDataStore.getTermsConditions()
        return termsConditionsMapper.mapTerms(entities)
    }

    override suspend fun isTermApproved(termCode: String): Boolean {
        val term = termsConditionsDataStore.getApproveTerms(termCode)
        return term.isNotNull()
    }

    override suspend fun isTermBlocked(termCode: String): Boolean {
        val term = termsConditionsDataStore.getApproveTerms(termCode)
        return (term?.active ?: false)
    }

    override suspend fun getTermUrl(termCode: String): String {
        val term = termsConditionsDataStore.getTerm(termCode)
        return term?.url.orEmpty()
    }
}
