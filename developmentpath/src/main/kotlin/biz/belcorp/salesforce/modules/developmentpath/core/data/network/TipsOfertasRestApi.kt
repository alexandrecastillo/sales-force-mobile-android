package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.base.BaseResponseGeneric
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TipsOfertasRestApi {

    @GET("FFVVOnlineService/api/consultora/ofertas/{pais}/{documento}/{campania}")
    fun obtenerTipsOfertas(
        @Path("pais") pais: String,
        @Path("documento") documento: String,
        @Path("campania") campania: String
    ): Single<BaseResponseGeneric<List<TipOfertaEntity>>>

}
