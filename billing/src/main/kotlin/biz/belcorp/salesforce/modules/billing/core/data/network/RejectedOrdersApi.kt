package biz.belcorp.salesforce.modules.billing.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RejectedOrdersApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getRejectedOrders(@Body request: String): Response<GraphResponse<RejectedOrdersDto.Data>>
}
