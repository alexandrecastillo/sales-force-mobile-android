package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.dto.DataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


internal interface SyncPostulantesApi {

    @GET("FFVVSyncService/api/sincronizacion/v2/unete/{ua}")
    suspend fun sync(@Path("ua") ua: String): Response<LegacySyncResponse<DataResponse>>

}
