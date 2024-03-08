package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource

import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PrePostulanteEntity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.data.network.CoordendasApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.UneteApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.ValidacionCrediticiaRestApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.ZonaContraseniaApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.*
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers.PostulanteEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers.PrePostulanteEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.FiltroBusqueda
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuro
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.InvalidMtoNameException
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.PostulanteRechazadaException
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ZonificacionNotFound
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.PostulanteUtil
import biz.belcorp.salesforce.modules.postulants.utils.capturarErrorApi
import com.google.gson.JsonObject
import io.reactivex.Single

class PostulantesCloudDataStore(
    private val uneteApi: UneteApi,
    private val zonaContraseniaApi: ZonaContraseniaApi,
    private val validacionCrediticiaApi: ValidacionCrediticiaRestApi,
    private val coordendasApi: CoordendasApi,
    private val postulanteUtil: PostulanteUtil,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun list(filtroBusqueda: FiltroBusqueda): Single<List<PostulanteEntity>> {

        val rb = PostulantesRequest()
        rb.pais = filtroBusqueda.pais
        rb.region = filtroBusqueda.region
        rb.zona = filtroBusqueda.zona
        rb.seccion = filtroBusqueda.seccion
        rb.rol = filtroBusqueda.rol
        rb.tipoFiltro = filtroBusqueda.tipoFiltro
        rb.textoBusqueda = filtroBusqueda.textoBusqueda

        return apiCallHelper.safeSingleApiCall { uneteApi.listarPostulantes(rb) }
            .map {
                PostulanteEntityDataMapper.map(it.resultado!!)
            }
    }

    fun listPre(
        pais: String, zona: String, seccion: String?, tipoFiltro: Int, textoBusqueda: String
    ): Single<List<PrePostulanteEntity>> {

        val rb = PrePostulantesRequest()
        rb.pais = Pais.find(pais)?.id.toString()
        rb.zona = zona
        rb.seccion = seccion
        rb.tipoFiltro = tipoFiltro
        rb.textoBusqueda = textoBusqueda

        return apiCallHelper.safeSingleApiCall { uneteApi.listarPrePostulantes(rb) }
            .map { PrePostulanteEntityDataMapper.map(it.resultado!!.resultado!!) }

    }

    fun geoBuroCO(
        pais: String, solicitudPostulanteID: Int, direccion: String,
        ciudad: String, subestadoPostulante: Int
    ): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.geoBuroCO(pais, solicitudPostulanteID, direccion, ciudad, subestadoPostulante)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun updateEstado(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?, tipoRechazo: String, motivoRechazo: String
    ): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.estado(
                pais,
                solicitudPostulanteID,
                estadoPostulante,
                subEstadoPostulante,
                tipoRechazo,
                motivoRechazo
            ).capturarErrorApi()
        }.map { it.respuesta }
    }

    fun updateEstadoPre(
        pais: String, solicitudPrePostulanteID: Int, tipoRechazo: String,
        motivoRechazo: String
    ): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.estadoPre(
                pais,
                solicitudPrePostulanteID,
                tipoRechazo,
                motivoRechazo
            )
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun validarBloqueos(pais: String, documento: String, tipoDocumento: String, zona: String)
        : Single<BuroResponseEntity> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.validarBloqueos(pais, documento, tipoDocumento, zona)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun validarBuros(validacionBuro: ValidacionBuro): Single<ValidacionResponseEntity> {
        validacionBuro.apply {
            return apiCallHelper.safeSingleApiCall {
                uneteApi.validarBuros(
                    pais, documento, zona, seccion, tipoDocumento, subestado, postulante
                ).capturarErrorApi()
            }.map { it.respuesta }
        }
    }

    fun obtenerNombreConsultora(pais: String, codigo: String): Single<String> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.obtenerNombreConsultora(pais, codigo)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun geo(pais: String, latitud: String, longitud: String, distrito: String): Single<String> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.geo(pais, latitud, longitud, distrito)
                .capturarErrorApi()
        }.map {
            it.takeIf {
                it.codigo?.trim() == "B"
            }?.respuesta ?: throw ZonificacionNotFound()
        }
    }

    fun create(postulante: PostulanteEntity): Single<Int> {
        return apiCallHelper.safeSingleApiCall {
            PostulantsLogger.log(postulante)
            uneteApi.agregarPostulante(postulante)
                .capturarErrorApi()
        }.map {
            when {
                it.respuesta ?: Constant.NUMERO_CERO > Constant.NUMERO_CERO -> it.respuesta
                it.respuesta == Constant.UNO_NEGATIVO -> throw PostulanteRechazadaException(it.mensaje)
                else -> throw ErrorException()
            }
        }
    }

    fun create(pais: String, postulantes: List<PostulanteEntity>): Single<Int> {

        val data = ListPostulanteApi()
        data.pais = pais
        data.postulantes = postulanteUtil.map64(postulantes)

        return apiCallHelper.safeSingleApiCall {
            PostulantsLogger.log(postulantes)
            uneteApi.agregarPostulante(data)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun agregarPostulanteFirma(postulante: PostulanteEntity): Single<Int> {
        return apiCallHelper.safeSingleApiCall {
            PostulantsLogger.log(postulante)
            uneteApi.agregarPostulanteFirma(postulante)
                .capturarErrorApi()
        }.map {
            when {
                it.respuesta ?: Constant.NUMERO_CERO > Constant.NUMERO_CERO -> it.respuesta
                it.respuesta == Constant.UNO_NEGATIVO -> throw PostulanteRechazadaException(it.mensaje)
                else -> throw ErrorException()
            }
        }
    }

    fun update(postulante: PostulanteEntity): Single<Int> {
        val p = postulanteUtil.map64(postulante)
        return apiCallHelper.safeSingleApiCall {
            PostulantsLogger.log(postulante)
            uneteApi.actualizarPostulante(p)
                .capturarErrorApi()
        }.map {
            when {
                it.respuesta ?: Constant.NUMERO_CERO > Constant.NUMERO_CERO -> it.respuesta
                it.respuesta == Constant.UNO_NEGATIVO -> throw PostulanteRechazadaException(it.mensaje)
                it.respuesta == Constant.DOS_NEGATIVO -> throw InvalidMtoNameException(it.mensaje)
                else -> throw ErrorException()
            }
        }
    }

    fun update(prePostulante: PrePostulanteEntity): Single<Int> {
        val p = prePostulante
        return apiCallHelper.safeSingleApiCall {
            uneteApi.actualizarPrePostulante(p)
                .capturarErrorApi()
        }.map {
            when {
                it.respuesta ?: Constant.NUMERO_CERO > Constant.NUMERO_CERO -> it.respuesta
                it.respuesta == Constant.UNO_NEGATIVO -> throw PostulanteRechazadaException(it.mensaje)
                else -> throw ErrorException()
            }
        }
    }

    fun update(pais: String, postulantes: List<PostulanteEntity>): Single<Int> {

        val data = ListPostulanteApi()
        data.pais = pais
        data.postulantes = postulanteUtil.map64(postulantes)
        data.postulantes?.forEach {
            it.indicadorActivo = "true"
        }

        return apiCallHelper.safeSingleApiCall {
            PostulantsLogger.log(postulantes)
            uneteApi.actualizarPostulante(data)
                .capturarErrorApi()
        }.map { it.respuesta?.takeIf { it != 0 } ?: throw ErrorException() }
    }

    fun getMensajeDevueltoSac(solicitudPostulanteID: Int): Single<String> {
        val devueltoSac = DevueltoSacRequest()
        devueltoSac.solicitudPostulanteId = solicitudPostulanteID
        return apiCallHelper.safeSingleApiCall {
            uneteApi.devueltoSac(devueltoSac)
        }.map { it.resultado?.observacion }
    }

    fun validarMail(codigoISO: String, mail: String, numeroDocumento: String): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.validarMail(codigoISO, mail, numeroDocumento)
        }.map { it.resultado }
    }

    fun validarCelular(
        codigoISO: String, celular: String, tipoDocumento: String, numeroDocumento: String
    ): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.validarCelular(codigoISO, celular, tipoDocumento, numeroDocumento)
        }.map { it.resultado }
    }

    fun enviarSMSValidacionTelefonica(
        codigoISO: String, solicitudPostulanteID: Int, celular: String,
        numeroDocumento: String, nombreCompleto: String
    ): Single<Boolean> {
        val entity = JsonObject().apply {
            addProperty("pais", codigoISO)
            addProperty("solicitudPostulanteID", solicitudPostulanteID)
            addProperty("numeroCelular", celular)
            addProperty("numeroDocumento", numeroDocumento)
            addProperty("nombreCompleto", nombreCompleto)
        }

        return apiCallHelper.safeSingleApiCall {
            uneteApi.enviarValidacionSMS(entity)
        }.map {
            !it.resultado.isNullOrEmpty()
        }
    }

    fun validarBloqueosMXPaso2(evaluacionBuroMXEntity: EvaluacionBuroMXEntity): Single<BuroResponseEntity> {

        val postulante = evaluacionBuroMXEntity.postulante

        val nombres = String.format("%s %s", postulante?.primerNombre, postulante?.segundoNombre)

        val params = mutableMapOf<String, String>()
        params["codigoISO"] = postulante?.pais.orEmpty()
        params["numeroDocumento"] = postulante?.numeroDocumento.orEmpty()
        params["apellido"] = postulante?.apellidoPaterno.orEmpty()
        params["codZona"] = postulante?.codigoZona.orEmpty()
        params["apellidoMaterno"] = postulante?.apellidoMaterno.orEmpty()
        params["nombres"] = nombres
        params["fechaNacimiento"] = evaluacionBuroMXEntity.fechaNacimiento
        params["direccion"] = evaluacionBuroMXEntity.direccion
        params["delegacionMunicipio"] = postulante?.lugarHijo.orEmpty()
        params["ciudad"] = evaluacionBuroMXEntity.ciudad
        params["estado"] = evaluacionBuroMXEntity.estado
        params["cp"] = postulante?.codigoPostal.orEmpty()
        params["tarjetaDeCredito"] = evaluacionBuroMXEntity.tarjetaDeCredito
        params["creditoHipotecario"] = evaluacionBuroMXEntity.creditoHipotecario
        params["creditoAutomotriz"] = evaluacionBuroMXEntity.creditoAutomotriz
        params["tipoIdentificacion"] = postulante?.tipoDocumento ?: Constant.NUMERO_UNO.toString()
        params["genero"] = postulante?.sexo.orEmpty()

        return apiCallHelper.safeSingleApiCall {
            uneteApi.validarBloqueosMXPaso2(params)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun eliminarPostulante(solicitudPostulanteID: Int): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.eliminarPostulante(solicitudPostulanteID)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun eliminarPrePostulante(solicitudPrePostulanteID: Int): Single<Boolean> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.eliminarPrePostulante(solicitudPrePostulanteID)
                .capturarErrorApi()
        }.map { it.respuesta }
    }

    fun obtenerPostulanteApta(
        pais: String, tipoDocumento: String,
        documento: String
    ): Single<PostulanteAptaApiResponse.PostulanteApta> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.obtenerPostulanteApta(pais, tipoDocumento, documento)
        }.map { it.resultado }
    }

    fun getVerificarEstadoTelefonoZona(
        codigoISO: String?, codigoZona: String?, codigoSeccion: String?
    ): Single<Int> {
        return apiCallHelper.safeSingleApiCall {
            zonaContraseniaApi.getVerificarEstadoTelefonoZona(codigoISO, codigoZona, codigoSeccion)
        }.map { it.estado }
    }

    fun validarPin(validarPinEntity: ValidarPinEntity): Single<Int> {
        return apiCallHelper.safeSingleApiCall {
            uneteApi.validarPin(
                validarPinEntity.solicitudPostulanteID,
                validarPinEntity.numeroDocumento,
                validarPinEntity.pais,
                validarPinEntity.PIN
            )
        }.map { it.resultado }
    }

    fun actualizarPostulanteProactivo(
        codigoIso: String,
        estado: Int,
        solicitudPostulanteId: String,
        motivoRechazo: String?
    ): Single<PostulanteProactivoResponse?>? {

        val request = PostulanteProactivoRequest()
        request.codigoIso = codigoIso
        request.estado = estado
        request.solicitudPostulanteId = solicitudPostulanteId
        request.motivoRechazo = motivoRechazo

        return try {
            uneteApi.actualizarPostulanteProactivo(request = request)
        } catch (ex: Exception) {
            null
        }
    }

    fun getCoordinatesByDirection(request: CoordenadasRequest): Single<CoordendasResponse> {
        return apiCallHelper.safeSingleApiCall {
            coordendasApi.getCoordinatesByDirection(request)
        }
    }

    fun obtenerSolicitudPostulanteEstados(
        codigoIso: String,
        solicitudPostulanteId: String,
    ): Single<SolicitudPostulanteEstadosResponse> {
        return validacionCrediticiaApi.obtenerSolicitudPostulanteEstados(codigoIso, solicitudPostulanteId)
    }

}
