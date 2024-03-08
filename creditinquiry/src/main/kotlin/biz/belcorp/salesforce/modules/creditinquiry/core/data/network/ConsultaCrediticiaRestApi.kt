package biz.belcorp.salesforce.modules.creditinquiry.core.data.network

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ValidarRegionZonaResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsultaCrediticiaRestApi {

    @GET("FFVVOnlineService/api/consultacrediticia/validarregionzona/{region}/{zona}")
    fun validarRegionZona(
        @Path("region") region: String,
        @Path("zona") zona: String
    ): Single<ValidarRegionZonaResponseEntity>

}
