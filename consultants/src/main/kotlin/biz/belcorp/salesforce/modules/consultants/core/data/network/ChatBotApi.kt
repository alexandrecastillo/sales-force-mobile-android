package biz.belcorp.salesforce.modules.consultants.core.data.network

import retrofit2.Response
import retrofit2.http.GET

interface ChatBotApi {
    @GET("currentWebRoot")
    suspend fun getChatBotUrl(): Response<String>
}
