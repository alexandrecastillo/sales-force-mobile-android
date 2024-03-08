package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.ffvv.data.net.dto.georreferencia.GeorreferenciaRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST


interface GeorreferenciaApi {

    @POST("FFVVOnlineService/api/consultora/insertargeoreferencia")
    fun guardarGeorreferencia(@Body request: GeorreferenciaRequest): Completable

}
