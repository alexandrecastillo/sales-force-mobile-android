package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeQuery

class TopSalesSeCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getTopSalesSe(query: TopSalesSeQuery): TopSalesSeDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getTopSalesSe(request)
        }
        return requireNotNull(response?.data)
    }

}
