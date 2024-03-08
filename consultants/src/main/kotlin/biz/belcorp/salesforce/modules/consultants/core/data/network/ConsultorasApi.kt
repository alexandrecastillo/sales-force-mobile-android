package biz.belcorp.salesforce.modules.consultants.core.data.network

import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ConsultorasApi {

    @GET("FFVVSyncService/api/sincronizacion/consultoras/{date}/{ua}")
    suspend fun sync(@Path("date") date: Long, @Path("ua") ua: String): Response<SyncResponse>

}
