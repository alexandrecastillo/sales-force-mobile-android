package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticia2Entity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticiaInternaEntity
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import io.reactivex.Single
import retrofit2.http.*

interface ValidacionCrediticiaRestApi {

    @GET("ValidacionCrediticiaExterna/Get")
    fun getConsultaCrediticiaDeudaExterna(
        @QueryMap options: Map<String, String>
    ): Single<ConsultaCrediticia2Entity>

    @GET("ValidacionCrediticiaInterna/Get")
    fun getConsultaCrediticiaDeudaInterna(
        @Query("codigoISO") codigoISO: String,
        @Query("numeroDocumento") numeroDocumento: String
    ): Single<ConsultaCrediticiaInternaEntity>

    @GET("portal/ObtenerSolicitudPostulanteEstados")
    fun obtenerSolicitudPostulanteEstados(
        @Query("codigoISO") codigoISO: String,
        @Query("solicitudPostulanteId") solicitudPostulanteId: String
    ): Single<SolicitudPostulanteEstadosResponse>

}
