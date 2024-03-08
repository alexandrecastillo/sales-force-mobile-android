package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteSubEstadoEnAprobacionFFVV
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.android.gms.maps.model.LatLng
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class UnetePaso3Presenter(
    private val useCase: PostulantesUseCase,
    private val mapPostulante: PostulantesModelDataMapper,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val useCaseParametroUnete: ParametroUneteUseCase,
    private val errorMessageFactory: ErrorMessageFactory
) : Presenter {

    private var mView: UnetePaso3View? = null
    private val sesion get() = obtenerSesionUseCase.obtener()
    private var isUpdatePostulanteBuro = false


    fun setView(vw: UnetePaso3View) {
        this.mView = vw
    }

    fun validateMapCircle() {
        var zonaSeccion = sesion.zona.orEmpty()
        if (sesion.seccion != null) zonaSeccion += sesion.seccion

        useCaseParametroUnete.getParametroUnete(
            RadioMapaSubscriber(), UneteTipoParametro.MAPARADIO.tipo, zonaSeccion
        )

    }

    override fun destroy() {
        useCase.dispose()
        mView = null
    }

    private fun showLoading() {
        mView?.showLoading()
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

    private fun updated(postulante: Postulante) {
        mapPostulante.map(postulante).let { mView?.updated(it) }
    }

    private fun notItSection(postulante: Postulante) {
        mapPostulante.map(postulante).let { mView?.notInSection(it) }
    }

    private inner class UpdateEstadoSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }
    }

    private inner class ValidarBurosSubscriber : BaseSingleObserver<ValidacionBuroResponse>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
        }

        override fun onSuccess(t: ValidacionBuroResponse) {
            hideViewLoading()
            if (t.enumEstadoCrediticio == UneteEstadoCrediticio.NO_PUEDE_SER_CONSULTORA.value) {
                mView?.validarZonaPagoDeContado(t)
            } else {
                mView?.validacionExitosa(t)
            }

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
            useCase.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
            useCase.updateEstado(
                UpdateEstadoSubscriber(), t.pais.orEmpty(),
                t.solicitudPostulanteID,
                t.estadoPostulante.orEmpty().toInt(),
                t.subEstadoPostulante, Constant.EMPTY_STRING, Constant.EMPTY_STRING
            )
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }


    private inner class UpdateOfflinePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            if (esGerentaZona() && !esDeZona(t.codigoZona.orEmpty())) {
                notItSection(t)
                return
            } else if (esSocia()) {
                if (!esDeZona(t.codigoZona.orEmpty())) {
                    notItSection(t)
                    return
                } else {
                    if (!esDeSeccion(t.codigoSeccion.orEmpty())) {
                        notItSection(t)
                        return
                    }
                }
            }

            if ((t.pais == Pais.CHILE.codigoIso || t.pais == Pais.PERU.codigoIso) && !isUpdatePostulanteBuro) {
                validarBuros(t)
                return
            }
            updated(t)
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }

    private inner class RadioMapaSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            if (y.isNotEmpty() && !y[0].valor.isNullOrEmpty())
                y[0].valor?.let { mView?.showMapRadio(it.toInt()) }
        }
    }

    private inner class GeoPostulanteSubscriber : BaseSingleObserver<GeoZonificacion>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            mView?.showZonificacion(exception as Exception)
        }

        override fun onSuccess(t: GeoZonificacion) {
            hideViewLoading()
            mView?.showZonificacion(t)
        }
    }

    fun updatePostulante(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        showLoading()
        useCase.update(UpdatePostulanteSubscriber(), p)
    }

    fun validarBuros(postulante: Postulante) {
        showLoading()
        useCase.validarBuros(
            ValidarBurosSubscriber(), validarBuroRequest(postulante)
        )
    }

    fun validarBuroRequest(postulante: Postulante): ValidacionBuro {

        val request = ValidacionBuro()
        request.pais = postulante.pais.orEmpty()
        request.documento = postulante.numeroDocumento.orEmpty()
        request.zona = postulante.codigoZona.orEmpty()
        request.seccion = postulante.codigoSeccion.orEmpty()
        request.tipoDocumento = postulante.tipoDocumento?.toInt() ?: Constant.NUMERO_UNO
        request.postulante = postulante.solicitudPostulanteID
        request.subestado = UneteSubEstadoEnAprobacionFFVV.POR_GZ.estado

        return request

    }

    fun geo(pais: String, latitud: String, longitud: String, distrito: String) {
        showLoading()
        this.useCase.geo(GeoPostulanteSubscriber(), pais, latitud, longitud, distrito)
    }

    fun esGerentaZona() = sesion.rol == Rol.GERENTE_ZONA

    fun esSocia() = sesion.rol == Rol.SOCIA_EMPRESARIA

    fun esDeSeccion(seccion: String) = sesion.seccion.orEmpty() == seccion

    fun esDeZona(zona: String) = sesion.zona == zona

    fun obtenerRadioActual(posicionCentral: LatLng, posicionFinal: LatLng): Double {
        val lat1 = posicionCentral.latitude
        val lat2 = posicionFinal.latitude
        val lon1 = posicionCentral.longitude
        val lon2 = posicionFinal.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) + (cos(Math.toRadians(lat1))
            * cos(Math.toRadians(lat2)) * sin(dLon / 2)
            * sin(dLon / 2))
        val c = 2 * asin(sqrt(a))
        return Constant.RADIO_PLANETA * c * 1000
    }

    fun setIsUpdatePostulanteBuro(isUpdatePostulanteBuro: Boolean) {
        this.isUpdatePostulanteBuro = isUpdatePostulanteBuro
    }
}
