package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoQuery

class TopSalesCoCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getTopSalesConsultant(query: TopSalesCoQuery): TopSalesCoDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getTopSalesConsultant(request)
        }
        return requireNotNull(response?.data)
    }

}
