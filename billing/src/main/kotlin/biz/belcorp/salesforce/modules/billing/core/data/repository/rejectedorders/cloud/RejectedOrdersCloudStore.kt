package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.billing.core.data.network.RejectedOrdersApi
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersDto
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersQuery
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersRequest

class RejectedOrdersCloudStore(
    private val rejectOrdersApi: RejectedOrdersApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getRejectedOrders(query: RejectedOrdersRequest): RejectedOrdersDto.Data {
        val request = RejectedOrdersQuery(query).get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            rejectOrdersApi.getRejectedOrders(request)
        }
        return requireNotNull(response?.data)
    }
}
