package biz.belcorp.salesforce.modules.calculator.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto.CampaignProjectionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CampaignProjectionApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getCampaignProjectionInfo(@Body request: String):
        Response<GraphResponse<CampaignProjectionDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveCampaignProjectionInfo(@Body request: String):
        Response<GraphResponse<CampaignProjectionDto>>
}
