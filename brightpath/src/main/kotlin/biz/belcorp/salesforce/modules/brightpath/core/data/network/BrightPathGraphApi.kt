package biz.belcorp.salesforce.modules.brightpath.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelDto
import retrofit2.Response
import retrofit2.http.*

interface BrightPathGraphApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getBusinessPartnerChangeLevel(@Body request: String): Response<GraphResponse<BusinessPartnerChangeLevelDto>>

}
