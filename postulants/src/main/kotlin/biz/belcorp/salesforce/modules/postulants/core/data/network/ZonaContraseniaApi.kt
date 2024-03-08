package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.ResponseZonaContrasenia
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ZonaContraseniaApi {

    @GET("portal/ObtenerZonaContrasenia")
    fun getVerificarEstadoTelefonoZona(@Query("codigoISO") codigoISO: String?,
                                       @Query("codigoZona")codigoZona: String?,
                                       @Query("codigoSeccion") codigoSeccion: String?): Single<ResponseZonaContrasenia>

}
