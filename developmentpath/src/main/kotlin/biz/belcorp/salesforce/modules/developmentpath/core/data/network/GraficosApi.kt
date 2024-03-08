package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos.GraficoGrResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GraficosApi {

    @GET("FFVVOnlineService/api/datosffvv/graficosgr/{region}")
    fun descargarGraficosGr(@Path("region") region: String): Single<GraficoGrResponse>
}
