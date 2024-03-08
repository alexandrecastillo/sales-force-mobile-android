package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CabeceraIndicadorUneteResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.DetalleIndicadorUneteResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.IndicadorUneteRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IndicadorApi {

    @Headers("Content-Type: application/json")
    @POST("FFVVOnlineService/api/consultora/listarindicadorunete")
    fun cabeceraIndicadorUnete(@Body cabeceraBody: IndicadorUneteRequest):
        Single<CabeceraIndicadorUneteResponse>

    @POST("FFVVOnlineService/api/consultora/detalleindicador")
    fun detalleIndicadorUnete(@Body detalleBody: IndicadorUneteRequest):
        Single<DetalleIndicadorUneteResponse>
}
