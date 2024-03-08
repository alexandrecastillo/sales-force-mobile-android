package biz.belcorp.salesforce.modules.consultants.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ConsultantsApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getConsultants(@Body request: String): Response<GraphResponse<ConsultantDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getDigitalInfo(@Body request: String): Response<GraphResponse<DigitalDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getOrdersAmount(@Body request: String): Response<GraphResponse<OrderAmountDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getMyPartners(@Body request: String): Response<GraphResponse<MyPartnersDto>>
}
