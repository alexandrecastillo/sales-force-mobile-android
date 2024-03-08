package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PrePostulante
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ObtenerNombreConsultoraRecomendanteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.mapper.PrePostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class UnetePrePaso2Presenter
constructor(
    private val useCasePostulante: PostulantesUseCase,
    private val obtenerNombreConsultoraRecomendanteUseCase: ObtenerNombreConsultoraRecomendanteUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val mapPrePostulante: PrePostulantesModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory
) : Presenter {

    var mView: UnetePaso2View? = null
    var postulante: PrePostulante? = null

    val sesion get() = obtenerSesionUseCase.obtener()

    fun setView(vw: UnetePaso2View) {
        this.mView = vw
    }

    override fun pause() {
        Constant.NOT_IMPLEMENTED
    }

    override fun destroy() {
        useCasePostulante.dispose()
        mView = null
    }

    override fun resume() {
        Constant.NOT_IMPLEMENTED
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

    private fun updated(prePostulante: PrePostulante) {
        mView?.updated(mapPrePostulante.map(prePostulante))
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

    private inner class UpdatePrePostulanteSubscriber : BaseSingleObserver<PrePostulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: PrePostulante) {
            hideViewLoading()
            useCasePostulante.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
        }
    }

    private inner class UpdateOfflinePostulanteSubscriber : BaseSingleObserver<PrePostulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: PrePostulante) {
            hideViewLoading()
            updated(t)
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }

    private inner class ValidarCelularSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.decidirActualizarOCrear()
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            if (t) {
                mView?.showDuplicateCelular()
            } else {
                mView?.decidirActualizarOCrear()
            }
        }
    }

    inner class EliminarPrePostulanteSubscriber : BaseSingleObserver<Boolean>() {

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

    fun obtenerNombreConsultora(codigoConsultora: String) {
        obtenerNombreConsultoraRecomendanteUseCase.obtener(
            codigoConsultora,
            ObtenerNombreConsultoraSubscriber()
        )
    }

    fun updatePrePostulanteOnline(postulante: PrePostulanteModel) {
        val p = mapPrePostulante.reverseMap(postulante)
        mView?.showLoading()
        useCasePostulante.update(UpdatePrePostulanteSubscriber(), p)
    }

    fun eliminarPrePostulante(solicitudPrePostulanteID: Int) {
        mView?.showLoading()
        useCasePostulante.eliminarPrePostulante(
            EliminarPrePostulanteSubscriber(),
            solicitudPrePostulanteID
        )
    }

    fun validarCelular(
        pais: String,
        celular: String,
        tipoDocumento: String,
        numeroDocumento: String
    ) {
        mView?.showLoading()
        useCasePostulante.validarCelular(
            ValidarCelularSubscriber(),
            pais,
            celular,
            tipoDocumento,
            numeroDocumento
        )
    }

}
