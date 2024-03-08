package biz.belcorp.salesforce.core.domain.repository.terms

import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.SaveTerms
import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions

interface TermsConditionsRepository {
    suspend fun syncTermsConditions(countryId: String,role: String)
    suspend fun syncApprovedTermsConditions(countryId: String,role: String,code: String,typeQuery: String)
    suspend fun getTermsConditions() : List<TermsConditions>
    suspend fun saveApprovedTerm(params: ApproveTermsParams) : Boolean
    suspend fun isTermApproved(termCode: String) : Boolean
    suspend fun isTermBlocked(termCode: String) : Boolean
    suspend fun getTermUrl(termCode: String) : String
}
