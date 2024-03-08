package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE_THOUSAND
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Timer
import biz.belcorp.salesforce.core.utils.formatMinutesAndSeconds
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.APROBADA_CON_CC
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.APROBADA_SIN_CC
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.NO_DEFINIDO
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.RECHAZADA_BURO_CREDITICIO
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.RECHAZADA_POR_CC
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.VALID_SIN_CC_ZONA_NO_CONFIG
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.*
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.PostulantTextResolver
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.PlaceModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.ValidarPinModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import java.text.SimpleDateFormat
import java.util.*

class UnetePaso2Presenter(
    private val useCasePostulante: PostulantesUseCase,
    private val useCaseParametroUnete: ParametroUneteUseCase,
    private val obtenerNombreConsultoraRecomendanteUseCase: ObtenerNombreConsultoraRecomendanteUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val mapPostulante: PostulantesModelDataMapper,
    private val mapValidarPin: ValidarPinModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory,
    private val mapsUseCase: MapsUseCase,
    private val placeModelDataMapper: PlaceModelDataMapper,
    private val bloqueosUseCase: ValidarBloqueosUseCase,
    private val textResolver: PostulantTextResolver
) : BasePresenter() {

    private var mView: UnetePaso2View? = null
    private var postulante: Postulante? = null
    private var validarPin: ValidarPin? = null
    private val sesion get() = obtenerSesionUseCase.obtener()
    private var isLocationMapByAdress = false

    fun setView(vw: UnetePaso2View) {
        this.mView = vw
    }

    override fun onDestroy() {
        useCasePostulante.dispose()
        timerSMS?.cancel()
        mView = null
    }

    fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?): Boolean {
        if (sesion.rol == Rol.SOCIA_EMPRESARIA) {
            return true
        }
        return !codigoConsultoraRecomendante.isNullOrEmpty()
    }

    fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?): String? {
        if (codigoConsultoraRecomendante.isNullOrEmpty()) {
            return obtenerCodigooDocumentoConsultoraRecomendanteSiSE()
        }
        return codigoConsultoraRecomendante
    }

    private fun obtenerCodigooDocumentoConsultoraRecomendanteSiSE(): String {
        return if (sesion.rol == Rol.SOCIA_EMPRESARIA) {
            if (sesion.countryIso == Pais.COLOMBIA.codigoIso) {
                sesion.person.document
            } else {
                sesion.codigoUsuario
            }
        } else {
            Constant.EMPTY_STRING
        }
    }

    private fun showErrorMessage(errorBundle: ErrorBundle) {
        errorMessageFactory.create(errorBundle.exception) {
            onDefaultError { mView?.showError(it) }
            onSolicitudRechazadaError { mView?.showSolicitudRechazada(it) }
        }
    }

    private fun hideViewLoading() {
        mView?.hideLoading()
    }

    private fun mostrarConsultoraRecomienda(consultora: String) {
        mView?.showConsultoraRecomienda(consultora)
    }

    private fun updated(postulante: Postulante) {
        mView?.updated(mapPostulante.map(postulante))
    }

    fun getBloqueoExterno(valueBloqueoExterno: Int): Boolean {
        return !(UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SIN_CONSULTAR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_CARTA_DESCARGO.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SUJETO_NO_ENCONTRADO.value == valueBloqueoExterno)
    }

    inner class ValidarBloqueosSubscriber : BaseSingleObserver<BuroResponse>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()

            val b = BuroResponse()
            b.bloqueosInternos = false
            b.enumEstadoCrediticioID = UneteEstadoCrediticio.SIN_CONSULTAR.value
            b.estadoCrediticio = true
            b.esConsultora = false
            b.esPostulante = false
            b.bloqueosExternos = false
            b.mensajeError = Constant.NULL_STRING
            b.estadoActividad = Constant.NUMERO_UNO
            b.nombreCompleto = Constant.EMPTY_STRING
            b.offline = true

            mView?.decidirActualizarOCrear(b)
        }

        override fun onSuccess(t: BuroResponse) {

            hideViewLoading()

            t.mensajeError =
                if (t.mensajeError == Constant.EMPTY_STRING) Constant.NULL_STRING else t.mensajeError

            if (getBloqueoExterno(t.enumEstadoCrediticioID ?: Constant.NUMERO_CERO)) {
                mView?.validarZonaPagoDeContado(t)
            } else {
                mView?.decidirActualizarOCrear()
            }
        }
    }

    private inner class ObtenerNombreConsultoraSubscriber : BaseSingleObserver<String>() {
        override fun onSuccess(t: String) {
            hideViewLoading()
            mostrarConsultoraRecomienda(t)
        }

        override fun onError(exception: Throwable) {
            mView?.errorAlObtenerNombreConsultoraRecomendante()
        }
    }

    private inner class UpdatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            useCasePostulante.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
        }
    }

    private inner class UpdateOfflinePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            updated(t)

            if (t.pais.orEmpty() == Pais.COLOMBIA.codigoIso) {

                val d = t.direccion?.replace(Constant.DIVIDE_SEPARATOR, " ").orEmpty()

                useCasePostulante.geoBuroCo(
                    GeoburoCOPostulanteSubscriber(),
                    t.pais.orEmpty(), t.solicitudPostulanteID, d, t.lugarHijo.orEmpty(),
                    UneteSubEstadoEnAprobacionFFVV.POR_GZ.estado
                )
            }

            Logger.loge(javaClass.simpleName, t.toString())
        }
    }

    private inner class UpdateOfflineMobileStatusConfirmedSuscriber :
        BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            when (t.estadoTelefonico) {
                UneteEstadoTelefonico.POR_VALIDAR.value -> enviarCodigo(t)
                UneteEstadoTelefonico.VALIDADO.value -> deshabilitarPIN()
                else -> hideViewLoading()
            }
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }

    private fun deshabilitarPIN() {
        hideViewLoading()
        mView?.deshabilitarValidarPIN()
    }

    private fun enviarCodigo(t: Postulante) {
        mView?.showLoading()
        enviarCodigoValidacionTelefonica(
            t.pais.orEmpty(),
            t.solicitudPostulanteID,
            t.celular.orEmpty(),
            t.numeroDocumento.orEmpty(),
            t.nombreCompleto
        )
    }

    private inner class ListLugarNivel1Subscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showLugarNivel1(y)
        }
    }

    private inner class ListLugarNivel2Subscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showLugarNivel2(y)
        }
    }

    private inner class ListLugarNivel3Subscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showLugarNivel3(y)
        }
    }


    private inner class ListLugarNivel4Subscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showLugarNivel4(y)
        }
    }

    private inner class ListLugarNivel5Subscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showLugarNivel5(y)
        }
    }

    private inner class ListTipoDireccionSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showTipoDireccion(y)
        }
    }

    private inner class GeoburoCOPostulanteSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }
    }


    private inner class ValidarCelularSubscriber(
        private val postulante: PostulanteModel
    ) : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.decidirActualizarOCrear()
        }

        override fun onSuccess(t: Boolean) {
            if (t) {
                hideViewLoading()

                mView?.showDuplicateCelular()
                return
            }

            validarUbicacionDireccion(postulante)
        }
    }

    private fun validarUbicacionDireccion(postulante: PostulanteModel) {
        if (!isLocationMapByAdress) {
            hideViewLoading()
            mView?.decidirActualizarOCrear()
            return
        }

        if (postulante.pais in PaisUnete.paisesConPlaceId && !postulante.hasChangedPlaceId) {
            hideViewLoading()
            mView?.decidirActualizarOCrear()
            return
        }

        if (!postulante.placeId.isNullOrBlank()) {
            mapsUseCase.buscarDetalleLugarGoogle(
                postulante.placeId.orEmpty(),
                GetDetailPlaceGoogleSubscribar()
            )
            return
        }

        val param = ParamGetCoordenadas().apply {
            direccion = postulante.direccion
            pais = postulante.pais
            ciudad = postulante.lugarHijo
            area = postulante.lugarPadre
        }

        useCasePostulante.getCoordinatesByDirection(param, GetCoordinatesByDirectionSubscriber())
    }

    private inner class GetDetailPlaceGoogleSubscribar : BaseSingleObserver<DetailPlace?>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: DetailPlace?) {
            hideViewLoading()

            if (t != null) {
                mView?.updateCoordinates(t.lat.toString(), t.lng.toString())
                return
            }

            mView?.decidirActualizarOCrear()
        }

    }

    private inner class GetCoordinatesByDirectionSubscriber : BaseSingleObserver<Coordenadas>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Coordenadas) {
            hideViewLoading()
            mView?.updateCoordinates(t.latitud, t.longitud)
        }
    }

    fun onUpdateCoordinates() {
        mView?.decidirActualizarOCrear()
    }

    private inner class EnviarSMSSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.showSMSExitosoDialog()
            mView?.showValidacionSMSFields()
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            mView?.showSMSExitosoDialog()
            mView?.showValidacionSMSFields()

        }
    }

    private inner class CreatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            PostulantQueue.removeServiceQueue(UneteServices.CREARPOSTULANTE)
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            PostulantQueue.removeServiceQueue(UneteServices.CREARPOSTULANTE)
            hideViewLoading()
            postulante?.let {
                useCasePostulante.addOffline(CreateOfflinePostulanteSubscriber(), it)
            }
        }
    }

    private inner class CreateOfflinePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            PostulantQueue.removeServiceQueue(UneteServices.CREARPOSTULANTE)
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            PostulantQueue.removeServiceQueue(UneteServices.CREARPOSTULANTE)
            hideViewLoading()
            created(t)

            if (t.pais.orEmpty() == Pais.COLOMBIA.codigoIso) {

                val d = t.direccion?.replace(Constant.DIVIDE_SEPARATOR, " ").orEmpty()

                useCasePostulante.geoBuroCo(
                    GeoburoCOPostulanteSubscriber(),
                    t.pais.orEmpty(), t.solicitudPostulanteID, d, t.lugarHijo.orEmpty(),
                    UneteSubEstadoEnAprobacionFFVV.POR_GZ.estado
                )
            }
        }
    }

    inner class ZonasCirculoCreditoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onSuccess(t: List<ParametroUnete>) {
            t.find { it.nombre == sesion.zona }?.also {
                mView?.showValidacionCrediticia()
            }

        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
        }

    }

    inner class RangosZonasSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.setRangosZonas(y)
        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
        }
    }

    private inner class BloqueosMexicoSubscriber : BaseSingleObserver<String>() {

        override fun onSuccess(t: String) {
            mView?.hideLoading()
            mView?.saveRepuestaBloqueos(t)
            when (t) {
                APROBADA_CON_CC -> mView?.showValidacionExitosa()
                APROBADA_SIN_CC -> mView?.showValidacionPendiente()
                RECHAZADA_POR_CC -> mView?.validarZonaPagoDeContado(null)
                VALID_SIN_CC_ZONA_NO_CONFIG -> mView?.showValidacionExitosa()
                NO_DEFINIDO -> mView?.showErrorConexion()
            }
        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
            mView?.hideLoading()
            mView?.showErrorConexion()
        }

    }

    inner class UpdateEstadoRechazadaSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            mView?.hideLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: Boolean) {
            mView?.hideLoading()
            if (t) {
                mView?.onPostulanteRechazada()
            }
        }

    }

    inner class EliminarPostulanteSubscriber : BaseSingleObserver<Boolean>() {

        override fun onSuccess(t: Boolean) {
            mView?.hideLoading()
            if (t) {
                mView?.onRegistroCancelado()
            }
        }

        override fun onError(exception: Throwable) {
            mView?.hideLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }
    }


    private fun created(postulante: Postulante) {
        if (esCAM(postulante.pais.orEmpty())) {
            if ((esGerentaZona() || esGerentaRegion()) && !esDeZona(postulante.codigoZona.orEmpty())) {
                mView?.notInSection()
                return
            } else if (esSocia()) {
                if (!esDeZona(postulante.codigoZona.orEmpty())) {
                    mView?.notInSection()
                    return
                } else {
                    if (!esDeSeccion(postulante.codigoSeccion.orEmpty())) {
                        mView?.notInSection()
                        return
                    }
                }
            }
        }
        mView?.created(mapPostulante.map(postulante))
    }

    fun obtenerNombreConsultora(codigoConsultora: String) {
        obtenerNombreConsultoraRecomendanteUseCase.obtener(
            codigoConsultora,
            ObtenerNombreConsultoraSubscriber()
        )
    }

    fun updatePostulante(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        mView?.showLoading()
        useCasePostulante.update(UpdatePostulanteSubscriber(), p)
    }

    fun updateEstadoTelefonico(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        mView?.showLoading()
        useCasePostulante.update(UpdateOfflineMobileStatusConfirmedSuscriber(), p)
    }

    fun createPostulante(postulanteModel: PostulanteModel) {
        postulante = mapPostulante.reverseMap(postulanteModel)
        mView?.showLoading()
        postulante?.let { useCasePostulante.add(CreatePostulanteSubscriber(), it) }
    }

    fun updateEstadoRechazada(solicitudPostulanteID: Int) {
        mView?.showLoading()
        useCasePostulante.updateEstado(
            UpdateEstadoRechazadaSubscriber(),
            sesion.countryIso,
            solicitudPostulanteID,
            UneteEstado.RECHAZADOS.estado,
            UneteSubEstadoRechazada.POR_CC.estado,
            RECHAZADA_BURO_CREDITICIO, Constant.EMPTY_STRING
        )
    }

    fun eliminarPostulante(solicitudPostulanteID: Int) {
        mView?.showLoading()
        useCasePostulante.eliminarPostulante(EliminarPostulanteSubscriber(), solicitudPostulanteID)
    }

    fun validarCelular(
        pais: String, celular: String, tipoDocumento: String, numeroDocumento: String, postulante: PostulanteModel
    ) {
        mView?.showLoading()
        useCasePostulante.validarCelular(
            ValidarCelularSubscriber(postulante), pais, celular, tipoDocumento, numeroDocumento
        )
    }

    fun enviarCodigoValidacionTelefonica(
        pais: String, solicitudPostulanteID: Int, celular: String,
        numeroDocumento: String, nombreCompleto: String
    ) {
        setupCountdown(pais)

        mView?.showLoading()
        useCasePostulante.enviarValidacionSMS(
            EnviarSMSSubscriber(),
            pais, solicitudPostulanteID, celular, numeroDocumento, nombreCompleto
        )
    }

    private var timerSMS: Timer? = null

    private fun setupCountdown(countryIso: String) {
        if(countryIso in PaisUnete.paisesContadorSMS) {
            mView?.disableReenviarSMS()

            timerSMS?.cancel()

            timerSMS = Timer(Constant.THIRTY_SECONDS).apply {
                onTick = {
                    mView?.updateCountdownSMS(formatSecondsToMmSs(it))
                }
                onFinish = {
                    mView?.onFinishCountdownSMS()
                }
            }.also {
                it.start()
            }
        }
    }

    fun listLugarPadre() {
        useCaseParametroUnete.list(ListLugarNivel1Subscriber(), UneteTipoParametro.LUGARNIVEL1.tipo)
    }

    fun lstLugarNivel2(id: Int) {
        useCaseParametroUnete.listByPadre(ListLugarNivel2Subscriber(), id)
    }

    fun lstLugarNivel3(id: Int) {
        useCaseParametroUnete.listByPadre(ListLugarNivel3Subscriber(), id)
    }

    fun lstLugarNivel4(id: Int) {
        useCaseParametroUnete.listByPadre(ListLugarNivel4Subscriber(), id)
    }

    fun lstLugarNivel5(id: Int) {
        useCaseParametroUnete.listByPadre(ListLugarNivel5Subscriber(), id)
    }

    fun lstTipoDireccion() {
        useCaseParametroUnete.list(
            ListTipoDireccionSubscriber(),
            UneteTipoParametro.LUGARNIVEL3.tipo
        )
    }

    fun listZonasAutomcompletadoDireccion() {
        useCaseParametroUnete.getParametroUnete(
            AutocompletadoDireccionSubscriber(), UneteTipoParametro.AUTOCOMPLETADO_DIRECCION.tipo, sesion.zona.orEmpty()
        )
    }

    private inner class AutocompletadoDireccionSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            if (y.isNotEmpty()) {
                mView?.autocompletadoDireccionNuevaExperiencia()
            }
        }
    }


    fun verificarZonasCirculoCredito() {
        if (sesion.countryIso == Pais.MEXICO.codigoIso) {
            useCaseParametroUnete.list(
                ZonasCirculoCreditoSubscriber(),
                UneteTipoParametro.ZONA.tipo
            )
        }
    }

    fun validarBloqueosMX(
        postulanteModel: PostulanteModel, estado: String, ciudad: String, direccion: String,
        tarjetaCredito: Boolean, creditoHipotecario: Boolean, creditoAutomotriz: Boolean
    ) {
        mView?.showLoading()

        val request = crearValidacionBloqueosMXRequest(
            estado, ciudad, direccion, tarjetaCredito, creditoHipotecario,
            creditoAutomotriz, postulanteModel
        )

        useCasePostulante.validarBloqueosMXPaso2(BloqueosMexicoSubscriber(), request)
    }

    private fun crearValidacionBloqueosMXRequest(
        estado: String, ciudad: String, direccion: String,
        tarjetaCredito: Boolean, creditoHipotecario: Boolean,
        creditoAutomotriz: Boolean, postulanteModel: PostulanteModel
    ): ValidacionBloqueosMX {
        val request = ValidacionBloqueosMX()
        request.estado = estado
        request.ciudad = ciudad
        request.direccion = direccion
        request.tarjetaDeCredito = tarjetaCredito
        request.creditoHipotecario = creditoHipotecario
        request.creditoAutomotriz = creditoAutomotriz
        request.postulante = mapPostulante.reverseMap(postulanteModel)
        return request
    }


    fun lstRangosZonas() {
        if (sesion.countryIso == Pais.MEXICO.codigoIso) {
            useCaseParametroUnete.list(RangosZonasSubscriber(), UneteTipoParametro.RANGOZONA.tipo)
        }
    }

    private fun esSocia() = sesion.rol == Rol.SOCIA_EMPRESARIA

    private fun esGerentaZona() = sesion.rol == Rol.GERENTE_ZONA

    private fun esGerentaRegion() = sesion.rol == Rol.GERENTE_REGION

    private fun esDeSeccion(seccion: String) = sesion.seccion == seccion

    private fun esDeZona(zona: String) = sesion.zona == zona

    private fun esCAM(pais: String) = pais in PaisUnete.paisesCam

    fun buscarLugares(codigoPais: String, cadenaBusqueda: String, localidad: Localidad?) {
        mapsUseCase.buscarLugares(codigoPais, cadenaBusqueda, Suscriber(localidad))
    }

    private inner class Suscriber(private val localidad: Localidad?) :
        BaseSingleObserver<List<Place>>() {
        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<Place>) {
            val placesFiltered = if (localidad != null) t.filter {
                it.description.contains(localidad.lugarPadre) && it.description.contains(localidad.lugarHijo)
            } else t

            mView?.mostrarPlaces(placeModelDataMapper.map(placesFiltered))
        }
    }

    fun validarPin(validarPinModel: ValidarPinModel) {
        validarPin = mapValidarPin.reverseMap(validarPinModel)
        mView?.showLoading()
        validarPin?.let { useCasePostulante.validarPin(ValidarPinSubscriber(), it) }
    }

    fun listLocationByGps() {
        useCaseParametroUnete.getParametroUnete(LocationByGpsSuscriber(), UneteTipoParametro.LOCATION_MAP_BY_ADRESS.tipo)
    }

    private inner class LocationByGpsSuscriber : BaseSingleObserver<List<ParametroUnete>>() {
        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            isLocationMapByAdress = y.firstOrNull()?.valor == Constant.NUMERO_UNO.toString()
        }
    }

    private inner class ValidarPinSubscriber : BaseSingleObserver<Int>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
            mView?.showErrorEnLaValidacion()
        }

        override fun onSuccess(t: Int) {
            hideViewLoading()
            mView?.showValidacionPINCompleta(t)
        }
    }

    fun validateStatePhone(codigoIso: String, solicitudPostulanteId: String) {
        useCasePostulante.obtenerSolicitudPostulanteEstados(ObtenerSolicitudPostulanteSubscriber(), codigoIso, solicitudPostulanteId)
    }

    private inner class ObtenerSolicitudPostulanteSubscriber : BaseSingleObserver<SolicitudPostulanteEstadosResponse>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
            mView?.showErrorEnLaValidacion()
            mView?.onNotVerifiedPhoneAfterCountdown()
        }

        override fun onSuccess(t: SolicitudPostulanteEstadosResponse) {
            hideViewLoading()

            mView?.onGetSolicitudPostulante(t)
        }
    }

    private inner class UpdateEstadoTelefonicoSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
            mView?.showErrorEnLaValidacion()
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()

            when(t.estadoTelefonico) {
                UneteEstadoTelefonico.VALIDADO.value -> deshabilitarPIN()
                else -> mView?.onNotVerifiedPhoneAfterCountdown()
            }
        }
    }

    fun validarCelularDuplicado(
        pais: String, celular: String, tipoDocumento: String, numeroDocumento: String
    ) {
        mView?.showLoading()
        useCasePostulante.validarCelular(
            ValidarCelularDuplicadoSubscriber(), pais, celular, tipoDocumento, numeroDocumento
        )
    }

    private inner class ValidarCelularDuplicadoSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()

        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            when (t) {
                true -> {
                    mView?.showDuplicateCelular()
                }
                false -> {
                    mView?.enviarCodigo()
                }
            }
        }
    }

    private fun formatSecondsToMmSs(seconds: Long): String {
        val secondInMillis: Long = seconds * NUMBER_ONE_THOUSAND

        val calendar = Calendar.getInstance().apply {
            clear()
            timeInMillis = secondInMillis
        }

        val format = SimpleDateFormat(
            formatMinutesAndSeconds,
            Locale.getDefault()
        )

        return format.format(calendar.time)
    }

    fun updateEstadosPostulante(postulante: PostulanteModel) {
        useCasePostulante.updateOffline(UpdateEstadoTelefonicoSubscriber(), mapPostulante.reverseMap(postulante))
    }

    fun validarDatosDireccionMexico(postulante: PostulanteModel) {
        validarUbicacionDireccion(postulante)
    }

}
