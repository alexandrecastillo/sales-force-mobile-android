package biz.belcorp.salesforce.core.data.network

import biz.belcorp.salesforce.core.data.repository.browser.cloud.dto.LoginResponseDto
import biz.belcorp.salesforce.core.data.repository.browser.cloud.dto.MyAcademyResponseDto
import retrofit2.Response
import retrofit2.http.*

interface WsSomosBelcorpApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("grant_type") grantType: String?,
        @Field("username") username: String?,
        @Field("pais") pais: String?,
        @Field("tipoAutenticacion") tipoAutenticacion: String?
    ): Response<LoginResponseDto>


    @GET("v1.2/Consultora/MiAcademia")
    suspend fun getMyAcademyUrl(@QueryMap request: HashMap<String, String?>): Response<MyAcademyResponseDto>

}
