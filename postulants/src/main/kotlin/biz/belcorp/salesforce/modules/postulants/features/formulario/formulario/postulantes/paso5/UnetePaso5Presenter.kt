package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.EstadoTelefonoZona
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UneteDocumentoFactory
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms


class UnetePaso5Presenter(
    private val useCase: PostulantesUseCase,
    val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val mapPostulante: PostulantesModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory,
    private val documentoFactory: UneteDocumentoFactory
) : Presenter {

    private var mView: UnetePaso5View? = null
    lateinit var postulanteModel: Postulante
    val sesion get() = obtenerSesionUseCase.obtener()

    fun setView(vw: UnetePaso5View) {
        this.mView = vw
    }

    fun getPais() = sesion.countryIso

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
        mView?.updated(mapPostulante.map(postulante))
    }

    private inner class UpdatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            useCase.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
            Logger.loge(this.javaClass.simpleName, t.toString())
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
            postulanteModel = t

            when (t.pais) {
                Pais.MEXICO.codigoIso -> verificarEstadoTelefonoZona(estadoTelefonoZonaRequest(t))
                else -> updated(t)
            }

            Logger.loge(this.javaClass.simpleName, t.toString())
        }
    }

    private inner class ObtenerZonaRiesgoSubscriber : BaseSingleObserver<ParametroUnete>() {

        override fun onSuccess(t: ParametroUnete) {
            val codigoPais = sesion.countryIso
            val nivelRiesgo = t.valor.orEmpty()
            mView?.setZonaRiesgo(UneteAlgorithms.esZonaRiesgo(codigoPais, nivelRiesgo))
        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
        }
    }

    private inner class ObtenerZonaCriticaSubscriber(private val zonaSeccion: String) :
        BaseSingleObserver<ParametroUnete>() {

        override fun onSuccess(t: ParametroUnete) {
            t?.let {
                mView?.setZonaCritica(t.nombre?.contains(zonaSeccion) ?: false)
            }
        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
            mView?.setZonaCritica(false)
        }
    }

    fun updatePostulante(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        showLoading()
        useCase.update(UpdatePostulanteSubscriber(), p)
    }

    fun validarZonaRiesgo(zonaSeccion: String) {
        parametroUneteUseCase.list(
            ObtenerZonaRiesgoSubscriber(),
            UneteTipoParametro.NIVELRIESGO.tipo,
            zonaSeccion
        )
    }

    fun validarZonaCritica(zonaSeccion: String) {
        parametroUneteUseCase.list(
            ObtenerZonaCriticaSubscriber(zonaSeccion),
            UneteTipoParametro.SECCIONESCRITICAS.tipo,
            zonaSeccion
        )
    }

    fun listarDocumentos(model: PostulanteModel, esZonaCritica: Boolean) {
        val listDocumentos = documentoFactory.getDocumentos(sesion.countryIso, model, esZonaCritica)
        mView?.loadDocumentos(listDocumentos)
    }

    fun verificarEstadoTelefonoZona(estadoTelefono: EstadoTelefonoZona) {
        useCase.verificarEstadoTelefono(VerificarEstadoTelefonoZonaSubscriber(), estadoTelefono)
    }

    private fun estadoTelefonoZonaRequest(postulante: Postulante): EstadoTelefonoZona {
        val estadoTelefonoZona = EstadoTelefonoZona()
        estadoTelefonoZona.codigoISO = postulante.pais
        estadoTelefonoZona.codigoZona = postulante.codigoZona
        estadoTelefonoZona.codigoSeccion = Constant.EMPTY_STRING
        return estadoTelefonoZona
    }

    private inner class VerificarEstadoTelefonoZonaSubscriber : BaseSingleObserver<Int>() {
        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
            hideViewLoading()
            updated(postulanteModel)
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Int) {
            hideViewLoading()
            mView?.setTextoEmailSMS(t)
            updated(postulanteModel)
            Logger.loge(this.javaClass.simpleName, t.toString())
        }
    }


}
