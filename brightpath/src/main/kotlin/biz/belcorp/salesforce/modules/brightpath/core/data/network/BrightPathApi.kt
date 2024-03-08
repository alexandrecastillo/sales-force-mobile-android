package biz.belcorp.salesforce.modules.brightpath.core.data.network

import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse
import retrofit2.Response
import retrofit2.http.*

interface BrightPathApi {

    @GET("FFVVSyncService/api/sincronizacion/nivel/0/{ua}")
    suspend fun sync(@Path("ua") ua: String): Response<SyncResponse>

}
