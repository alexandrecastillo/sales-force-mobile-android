package biz.belcorp.salesforce.modules.digital.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DigitalApi {

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getDigitalInfo(@Body request: String): Response<GraphResponse<DigitalDto>>

}
