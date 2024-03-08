package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.RutaApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApi {

    @GET
    fun rutaOptima(@Url url: String): Single<RutaApiResponse>

}
