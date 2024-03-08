package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CoordenadasRequest
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteProactivoResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import io.reactivex.Completable
import io.reactivex.Single

interface PostulantesRepository {

    fun download(filtroBusqueda: FiltroBusqueda): Single<Int>
    fun downloadPre(request: RequestDownloadPre): Single<Int>
    fun list(estados: List<String>, seccion: String?): Single<List<Postulante>>
    fun listPre(estados: List<String>, seccion: String?): Single<List<PrePostulante>>
    fun listAll(): Single<List<Postulante>>
    fun add(postulante: Postulante): Single<Postulante>
    fun agregarPostulanteFirma(postulante: Postulante): Single<Postulante>
    fun addOffline(postulante: Postulante): Single<Postulante>
    fun update(postulante: Postulante): Single<Postulante>
    fun update(prePostulante: PrePostulante): Single<PrePostulante>
    fun updateOffline(postulante: Postulante): Single<Postulante>
    fun updateOffline(prePostulante: PrePostulante): Single<PrePostulante>
    fun uploadOfflineCreated(pais: String, list: List<Postulante>): Single<Int>
    fun uploadOfflineUpdated(pais: String, list: List<Postulante>): Single<Int>
    fun listOffline(): Single<List<Postulante>>
    fun get(uuid: String, id: Int): Single<Postulante>
    fun getPre(uuid: String, id: Int): Single<PrePostulante>
    fun geo(pais: String, latitud: String, longitud: String, distrito: String): Single<GeoZonificacion>

    fun geoBuroCO(
        pais: String, solicitudPostulanteID: Int, direccion: String,
        ciudad: String, subestadoPostulante: Int
    ): Single<Boolean>

    fun obtenerNombreConsultora(pais: String, codigo: String): Single<String>

    fun validarBloqueos(
        pais: String, documento: String, tipoDocumento: String, zona: String
    ): Single<BuroResponse>

    fun validarBuros(validacionBuro: ValidacionBuro): Single<ValidacionBuroResponse>

    fun updateEstado(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?, tipoRechazo: String, motivoRechazo: String
    ): Single<Boolean>

    fun updateEstadoPre(pais: String, solicitudPrePostulanteID: Int, tipoRechazo: String,
                        motivoRechazo: String): Single<Boolean>

    fun getObservacionDevueltoSac(solicitudPostulanteID: Int): Single<String>

    fun getVerificarEstadoTelefonoZona(
        codigoISO: String?, codigoZona: String?, codigoSeccion: String?
    ): Single<Int>

    fun existeNumeroDocumento(numeroDocumento: String): Single<Boolean>

    fun validarMail(codigoISO: String, mail: String, numeroDocumento: String): Single<Boolean>

    fun validarCelular(
        codigoISO: String, celular: String, tipoDocumento: String, numeroDocumento: String
    ): Single<Boolean>

    fun enviarValidacionSMSCelular(
        codigoISO: String, solicitudPostulanteID: Int, celular: String,
        numeroDocumento: String, nombreCompleto: String
    ): Single<Boolean>


    fun validarBloqueosMXPaso2(evaluacionBuroMX: EvaluacionBuroMX): Single<String>
    fun eliminarPostulante(solicitudPostulanteID: Int): Single<Boolean>
    fun eliminarPrePostulante(solicitudPrePostulanteID: Int): Single<Boolean>

    fun postulanteApta(
        pais: String, tipoDocumento: String, documento: String
    ): Single<PostulanteAptaResponse>

    fun sincronizar(): Completable

    fun validarPin(validarPin: ValidarPin): Single<Int>

    fun actualizarPostulanteProactivo(
        codigoIso: String,
        estado: Int,
        solicitudPostulanteId: String,
        motivoRechazo: String?
    ): Single<PostulanteProactivoResponse?>?

    fun getCoordinatesByDirection(request: CoordenadasRequest): Single<Coordenadas>
    
    fun obtenerSolicitudPostulanteEstados(
        codigoISO: String,
        solicitudPostulanteID: String
    ): Single<SolicitudPostulanteEstadosResponse>
}
