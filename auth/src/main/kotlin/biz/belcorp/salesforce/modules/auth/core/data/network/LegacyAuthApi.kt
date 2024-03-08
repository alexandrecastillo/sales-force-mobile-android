package biz.belcorp.salesforce.modules.auth.core.data.network

import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.AuthLegacyResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LegacyAuthApi {

    @FormUrlEncoded
    @POST("FFVVAuth/token2")
    suspend fun token(
        @Field("grant_type") grandType: String = GRANT_TYPE,
        @Field("pais") pais: String,
        @Field("usuario") usuario: String,
        @Field("rol") rol: String
    ): Response<AuthLegacyResponse>

    companion object {
        const val GRANT_TYPE = "password"
    }

}
