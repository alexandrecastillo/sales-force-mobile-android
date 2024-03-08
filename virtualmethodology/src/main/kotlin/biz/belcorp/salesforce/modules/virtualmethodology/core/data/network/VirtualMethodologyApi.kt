package biz.belcorp.salesforce.modules.virtualmethodology.core.data.network

import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.dto.SyncResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface VirtualMethodologyApi {

    @GET("FFVVOnlineService/api/datosffvv/metodologiavirtual/{country}/{ua}")
    suspend fun download(@Path("country") country: String, @Path("ua") ua: String): Response<SyncResponse>

}
