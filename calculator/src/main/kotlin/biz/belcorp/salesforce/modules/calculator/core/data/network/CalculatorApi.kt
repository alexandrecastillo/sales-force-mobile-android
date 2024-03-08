package biz.belcorp.salesforce.modules.calculator.core.data.network

import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.dto.CalculatingResultResponse
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.dto.SyncResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface CalculatorApi {

    @GET("FFVVSyncService/api/sincronizacion/calculadora/{date}/{ua}")
    suspend fun sync(
        @Path("date") date: Long, @Path("ua") ua: String): Response<SyncResponse>

    @POST("FFVVOnlineService/api/datosffvv/resultadocalculadora")
    suspend fun saveCalculatingResult(@Body request: CalculatingResultEntity): Response<CalculatingResultResponse>

}
