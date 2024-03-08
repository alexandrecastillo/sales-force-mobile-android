package biz.belcorp.salesforce.modules.postulants.core.data.network

import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PrePostulanteEntity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.BuroResponseEntity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.ValidacionResponseEntity
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.*
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.*

interface UneteApi {

    @POST("FFVVOnlineService/api/consultora/solicitudespostulantes")
    fun listarPostulantes(@Body request: PostulantesRequest):
        Single<PostulantesResponse>

    @POST("FFVVOnlineService/api/unete/solicitudesprepostulantes")
    fun listarPrePostulantes(@Body request: PrePostulantesRequest):
        Single<PrePostulantesResponse>

    @POST("FFVVOnlineService/api/unete/postulantes")
    fun agregarPostulante(@Body postulante: PostulanteEntity):
        Single<ResponseAPI<Int>>

        @POST("FFVVOnlineService/api/unete/postulantes/firma")
    fun agregarPostulanteFirma(@Body postulante: PostulanteEntity):
        Single<ResponseAPI<Int>>

    @POST("FFVVOnlineService/api/unete/postulantes/offline")
    fun agregarPostulante(@Body data: ListPostulanteApi):
        Single<ResponseAPI<Int>>

    @POST("FFVVOnlineService/api/unete/postulantes/upd")
    fun actualizarPostulante(@Body postulante: PostulanteEntity):
        Single<ResponseAPI<Int>>

    @POST("FFVVOnlineService/api/unete/prepostulantes/upd")
    fun actualizarPrePostulante(@Body prePostulante: PrePostulanteEntity):
        Single<ResponseAPI<Int>>

    @POST("FFVVOnlineService/api/unete/postulantes/offline/upd")
    fun actualizarPostulante(@Body data: ListPostulanteApi):
        Single<ResponseAPI<Int>>

    @GET("FFVVOnlineService/api/unete/geoburoCO")
    fun geoBuroCO(
        @Query("pais") pais: String,
        @Query("solicitudPostulanteID") solicitudPostulanteID: Int,
        @Query("direccion") direccion: String,
        @Query("ciudad") ciudad: String,
        @Query("subestadoPostulante") subestadoPostulante: Int
    ):
        Single<ResponseAPI<Boolean>>

    @GET("FFVVOnlineService/api/unete/geo")
    fun geo(
        @Query("pais") pais: String,
        @Query("latitud") latitud: String,
        @Query("longitud") longitud: String,
        @Query("distrito") distrito: String
    ):
        Single<ResponseAPI<String>>

    @GET("FFVVOnlineService/api/unete/postulantes/bloqueos")
    fun validarBloqueos(
        @Query("pais") pais: String,
        @Query("documento") documento: String,
        @Query("tipoDocumento") tipoDocumento: String,
        @Query("zona") zona: String
    ):
        Single<ResponseAPI<BuroResponseEntity>>


    @GET("FFVVOnlineService/api/unete/postulantes/bloqueos/buro")
    fun validarBuros(
        @Query("pais") pais: String,
        @Query("documento") documento: String,
        @Query("zona") zona: String,
        @Query("seccion") seccion: String,
        @Query("tipodocumento") tipoDocumento: Int,
        @Query("subestado") subEstado: Int,
        @Query("postulante") postulante: Int
    ):
        Single<ResponseAPI<ValidacionResponseEntity>>

    @GET("FFVVOnlineService/api/unete/postulantes/estado")
    fun estado(
        @Query("pais") pais: String,
        @Query("solicitudPostulanteID") solicitudPostulanteID: Int,
        @Query("estadoPostulante") estadoPostulante: Int,
        @Query("subEstadoPostulante") subEstadoPostulante: Int?,
        @Query("tipoRechazo") tipoRechazo: String,
        @Query("motivoRechazo") motivoRechazo: String
    ):
        Single<ResponseAPI<Boolean>>

    @GET("FFVVOnlineService/api/unete/prepostulante/rechazar")
    fun estadoPre(
        @Query("codigoISO") codigoISO: String,
        @Query("solicitudPrePostulanteID") solicitudPrePostulanteID: Int,
        @Query("tipoRechazo") tipoRechazo: String,
        @Query("motivoRechazo") motivoRechazo: String
    ):
        Single<ResponseAPI<Boolean>>

    @POST("FFVVOnlineService/api/consultora/solicitudpostulanteobs")
    fun devueltoSac(@Body devueltoSac: DevueltoSacRequest):
        Single<DevueltoSacResponse>

    @GET("FFVVOnlineService/api/unete/postulantes/ObtenerSolicitudPostulantePorCorreo")
    fun validarMail(
        @Query("pais") codigoISO: String,
        @Query("correoElectronico") correoElectronico: String?,
        @Query("numeroDocumento") numeroDocumento: String?
    ):
        Single<ValidarDataResponse>

    @GET("FFVVOnlineService/api/unete/postulantes/VerificarExisteCelular")
    fun validarCelular(
        @Query("pais") codigoISO: String,
        @Query("numeroCelular") numeroCelular: String?,
        @Query("tipoDocumento") tipoDocumento: String?,
        @Query("numeroDocumento") numeroDocumento: String?
    ):
        Single<ValidarDataResponse>

    @Headers("Content-Type: application/json")
    @POST("FFVVOnlineService/api/unete/postulantes/EnviarCodigoVerificacionTelefonica")
    fun enviarValidacionSMS(@Body validacionSMS: JsonObject):
        Single<EnvioSMSResponse>

    @GET("FFVVOnlineService/api/unete/postulantes/bloqueos/MX/paso2")
    fun validarBloqueosMXPaso2(@QueryMap options: Map<String, String>): Single<ResponseAPI<BuroResponseEntity>>

    @POST("FFVVOnlineService/api/unete/postulantes/delete")
    fun eliminarPostulante(@Query("solicitudPostulanteID") solicitudId: Int): Single<ResponseAPI<Boolean>>

    @POST("FFVVOnlineService/api/unete/postulantes/delete")
    fun eliminarPrePostulante(@Query("solicitudPostulanteID") solicitudId: Int): Single<ResponseAPI<Boolean>>

    @GET("FFVVOnlineService/api/unete/consultoras/{pais}/{codigo}")
    fun obtenerNombreConsultora(
        @Path("pais") pais: String,
        @Path("codigo") codigo: String
    ):
        Single<ResponseAPI<String>>

    @GET("FFVVOnlineService/api/unete/postulantes/postulanteapta")
    fun obtenerPostulanteApta(
        @Query("pais") pais: String,
        @Query("tipoDocumento") tipoDocumento: String?,
        @Query("documento") documento: String
    ):
        Single<PostulanteAptaApiResponse>

    @POST("FFVVOnlineService/api/unete/postulantes/ValidarCodigoVerificacionTelefonica")
    fun validarPin(
        @Query("solicitudPostulanteID") solicitudPostulanteID: Int?,
        @Query("numeroDocumento") numeroDocumento: String?,
        @Query("pais") pais: String?,
        @Query("PIN") pin: String?
    ): Single<ValidarPinResponse>

    @POST("FFVVOnlineService/api/consultora/estadopostulanteproactivo")
    fun actualizarPostulanteProactivo(@Body request: PostulanteProactivoRequest):
        Single<PostulanteProactivoResponse?>?

}
