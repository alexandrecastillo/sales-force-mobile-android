package biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.consultants.core.data.network.ConsultantsApi
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountQuery


class OrdersAmountCloudStore(
    private val consultantsApi: ConsultantsApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getOrdersAmount(query: OrderAmountQuery): List<OrderAmountDto.OrderAmount> {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            consultantsApi.getOrdersAmount(request)
        }
        return requireNotNull(response?.data?.amounts)
    }

}
