package biz.belcorp.salesforce.modules.auth.core.data.network

import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.dto.*
import retrofit2.Response
import retrofit2.http.*


interface AuthApi {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: AuthLoginRequest): Response<AuthLoginResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/login_support")
    suspend fun support(@Body request: AuthLoginRequest): Response<AuthLoginResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/new_access_token")
    suspend fun refreshToken(@Body request: AuthNewTokenRequest): Response<AuthNewTokenResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/valid_access_token")
    suspend fun validateToken(@Body request: AuthValidateTokenRequest): Response<AuthValidateTokenResponse>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun token(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String
    ): Response<AuthTokenResponse>

}
