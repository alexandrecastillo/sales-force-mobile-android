package biz.belcorp.salesforce.core.data.repository.terms.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.TermDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms.ApproveTermsQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms.SaveTermsQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionsQuery

class TermsConditionsCloudStore(
    private val termConditionsApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun downloadTermsConditions(query: TermsConditionsQuery): List<TermsConditionDto.Terms> {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeLegacyApiCall {
            termConditionsApi.getTerms(request)
        }
        return response?.data?.terms ?: emptyList()
    }

    suspend fun downloadApproveTerms(query: ApproveTermsQuery): List<TermDto> {
        val request = query.get().toRequestString()
        return apiCallHelper.safeLegacyApiCall {
            termConditionsApi.getApprovedTerms(request)
        }?.data?.terms?.approvedTerms ?: emptyList()
    }

    suspend fun saveApproveTerms(query: SaveTermsQuery): List<TermDto> {
        val request = query.get().toRequestString()
        return apiCallHelper.safeLegacyApiCall {
            termConditionsApi.saveApprovedTerms(request)
        }?.data?.approveTerm?.terms ?: emptyList()
    }

}
