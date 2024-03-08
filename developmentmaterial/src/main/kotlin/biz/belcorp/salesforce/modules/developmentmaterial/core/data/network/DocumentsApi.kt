package biz.belcorp.salesforce.modules.developmentmaterial.core.data.network

import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.dto.SyncResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DocumentsApi {

    @GET("FFVVOnlineService/api/consultora/getmaterialdesarrollo/{rol}")
    suspend fun download(@Path("rol") rol: String): Response<SyncResponse>

}
