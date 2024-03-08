package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersQuery

class SaleOrdersCloudStore(
    private val salesOrdersApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    companion object {
        private const val TAG_REQUEST = "KPIS-SalesOrdersAPI => Request"
    }

    suspend fun getSalesOrders(query: SaleOrdersQuery): SaleOrdersDto {
        val request = query.get().toRequestString()
        Logger.d(TAG_REQUEST, request)
        val response = apiCallHelper.safeBelcorpApiCall {
            salesOrdersApi.getSalesOrders(request)
        }
        return requireNotNull(response?.data)
    }
}
