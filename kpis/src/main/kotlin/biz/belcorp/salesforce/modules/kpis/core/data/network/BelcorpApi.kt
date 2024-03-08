package biz.belcorp.salesforce.modules.kpis.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.RetentionDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto.NewCycleQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BelcorpApi {

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getProfit(@Body request: String): Response<GraphResponse<ProfitDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getCapitalization(@Body request: String): Response<GraphResponse<CapitalizationDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getCollection(@Body request: String): Response<GraphResponse<CollectionDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getSalesOrders(@Body request: String): Response<GraphResponse<SaleOrdersDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getKpiNewCycle(@Body request: String): Response<GraphResponse<NewCycleQuery.Data>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getPostulantsKpi(@Body request: String): Response<GraphResponse<PostulantsKpiDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getBonificationInfo(@Body request: String): Response<GraphResponse<BonusDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getRetention(@Body request: String): Response<GraphResponse<RetentionDto>>


}
