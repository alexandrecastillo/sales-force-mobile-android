package biz.belcorp.salesforce.base.core.data.network

import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface BelcorpApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getOptionsMenu(@Body request: String): Response<GraphResponse<OptionsQuery.Data>>
}
