package biz.belcorp.salesforce.modules.creditinquiry.core.data.network

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultaCrediticia2Entity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultaCrediticiaInternaEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ValidacionCrediticiaRestApi {

    @GET("ValidacionCrediticiaExterna/Get")
    fun getConsultaCrediticiaDeudaExterna(
        @QueryMap query: HashMap<String, String>
    ): Single<ConsultaCrediticia2Entity>

    @GET("ValidacionCrediticiaInterna/Get")
    fun getConsultaCrediticiaDeudaInterna(
        @Query("codigoISO") codigoISO: String,
        @Query("numeroDocumento") numeroDocumento: String
    ): Single<ConsultaCrediticiaInternaEntity>

}
