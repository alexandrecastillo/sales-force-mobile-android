package biz.belcorp.salesforce.modules.inspires.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.modules.inspires.core.data.repository.sync.dto.DataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface InspiresApi {

    @GET("FFVVSyncService/api/sincronizacion/inspira/{date}/{ua}")
    suspend fun sync(
        @Path("date") date: Long,
        @Path("ua") ua: String
    ): Response<LegacySyncResponse<DataResponse>>

}
