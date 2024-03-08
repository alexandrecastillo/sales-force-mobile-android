package biz.belcorp.salesforce.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.GraphResponse
import biz.belcorp.salesforce.core.data.network.dto.UaResponse
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerDto
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto.ConfigurationQuery
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryDto
import biz.belcorp.salesforce.core.data.repository.session.cloud.dto.ProfileQuery
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.dto.SalesForceStatusDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.approveterms.ApproveTermsDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms.SaveTermsDto
import biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms.TermsConditionDto
import retrofit2.Response
import retrofit2.http.*


interface BelcorpApi {

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getProfileInfo(@Body request: String): Response<GraphResponse<ProfileQuery.Data>>

    @Headers("Content-Type: application/json")
    @GET("countries/{country}")
    suspend fun getUa(
        @Path("country") country: String,
        @QueryMap request: Map<String, String>
    ): Response<List<UaResponse>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getConfiguration(@Body request: String): Response<GraphResponse<ConfigurationQuery.Data>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getBusinessPartners(@Body request: String): Response<GraphResponse<BusinessPartnerDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getManagerDirectory(@Body request: String): Response<GraphResponse<ManagerDirectoryDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getTerms(@Body request: String): Response<GraphResponse<TermsConditionDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun getApprovedTerms(@Body request: String): Response<GraphResponse<ApproveTermsDto>>

    @Headers("Content-Type: application/json")
    @POST("sales_force/graphql")
    suspend fun saveApprovedTerms(@Body request: String): Response<GraphResponse<SaveTermsDto>>

    //This Request is for get the saleforce status of one Socia Empresaria
    @GET("reports/sales_force/status/{countryCode}")
    suspend fun getReportSalesForceStatus(
        @Path("countryCode") countryCode: String,
        @Query("region") region: String,
        @Query("zone") zone: String,
        @Query("section") section: String,
        @Query("profile") profile: String,
        @Query("campaigns") campaigns: String
    ): Response<List<SalesForceStatusDto>>

}
