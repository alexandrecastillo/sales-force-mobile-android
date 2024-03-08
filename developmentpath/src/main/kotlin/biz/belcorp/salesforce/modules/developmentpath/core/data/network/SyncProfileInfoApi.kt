package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleSeDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream.DreamCreateDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.dto.DreamConsultantDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream.DreamEditDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate.DreamCreateBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream.DreamEditBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto.ProfileCapitalizationDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto.ProfileSeOrdersU6CDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto.ProfileProfitDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SyncProfileInfoApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getSaleConsultants(@Body request: String): Response<GraphResponse<SaleConsultantDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getDigitalSaleConsultant(@Body request: String): Response<GraphResponse<DigitalSaleCoDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getTopSalesConsultant(@Body request: String): Response<GraphResponse<TopSalesCoDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getTopSalesSe(@Body request: String): Response<GraphResponse<TopSalesSeDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getDigitalSaleBusinessPartner(@Body request: String): Response<GraphResponse<DigitalSaleSeDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getProfileSeOrdersU6C(@Body request: String): Response<GraphResponse<ProfileSeOrdersU6CDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getProfileSeCapitalizationU6C(@Body request: String): Response<GraphResponse<ProfileCapitalizationDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getProfileRanking(@Body request: String): Response<GraphResponse<RankingDto>>

    @Headers("Content-Type: application/json")
    @POST("kpis/sales_force/graphql")
    suspend fun getProfileProfit(@Body request: String): Response<GraphResponse<ProfileProfitDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getTransactionAccount(@Body request: String): Response<GraphResponse<TransactionAccountDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getConsultantDreams(@Body request: String): Response<GraphResponse<DreamConsultantDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun deleteDream(@Body request: String): Response<GraphResponse<DreamConsultantDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveConsultantDreams(@Body request: String): Response<GraphResponse<DreamCreateDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveEditDreams(@Body request: String): Response<GraphResponse<DreamEditDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getBpDreams(@Body request: String): Response<GraphResponse<DreamBpDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveBusinessPartnerDream(@Body request: String): Response<GraphResponse<DreamCreateBpDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun deleteBusinessPartnerDream(@Body request: String): Response<GraphResponse<DreamBpDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveEditBusinessPartnerDreams(@Body request: String):
        Response<GraphResponse<DreamEditBpDto>>
}
