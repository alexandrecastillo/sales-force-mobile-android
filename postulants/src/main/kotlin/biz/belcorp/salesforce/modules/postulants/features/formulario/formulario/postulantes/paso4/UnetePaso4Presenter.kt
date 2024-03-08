package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.tipo.TipoMeta
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTablaLogica
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.InvalidMtoNameException
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.*
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TablaLogicaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TipoMetaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms

class UnetePaso4Presenter(
    private val useCase: PostulantesUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val obtenerNombreConsultoraRecomendanteUseCase: ObtenerNombreConsultoraRecomendanteUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val tablaLogicaUseCase: TablaLogicaUseCase,
    private val tipoMetaUseCase: TipoMetaUseCase,
    private val mapTipoMeta: TipoMetaModelDataMapper,
    private val mapTablaLogica: TablaLogicaModelDataMapper,
    private val mapPostulante: PostulantesModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory
) : Presenter {

    val sesion get() = obtenerSesionUseCase.obtener()

    var mView: UnetePaso4View? = null

    fun setView(vw: UnetePaso4View) {
        this.mView = vw
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
            if (errorBundle.exception is InvalidMtoNameException) {
                onDefaultError { mView?.showError(errorBundle.exception.message ?: it) }
            } else {
                onSolicitudRechazadaError { mView?.showSolicitudRechazada(it) }
            }
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
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            useCase.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
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
            updated(t)
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }


    private inner class ListTipoVinculoFamiliarSubscriber :
        BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showTipoVinculoFamiliar(y)
        }
    }

    private inner class ListEstadoCivilSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showEstadoCivil(y)
        }
    }

    private inner class ListTipoVinculoNoFamiliarSubscriber :
        BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showTipoVinculoNoFamiliar(y)
        }
    }

    private inner class ListTipoVinculoAvalSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showTipoVinculoAval(y)
        }
    }

    private inner class ListNivelEducativoSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showNivelEducativo(y)
        }
    }

    private inner class GetZonaRiesgoSubscriber : BaseSingleObserver<ParametroUnete>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: ParametroUnete) {
            val codigoPais = sesion.countryIso
            mView?.setZonaRiesgo(UneteAlgorithms.esZonaRiesgo(codigoPais, t.valor ?: ""))
        }
    }

    private inner class ListOrigenIngresoSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showOrigenIngreso(y)
        }
    }

    private inner class ListTipoPersonaSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showTipoPersona(y)
        }
    }

    private inner class ListTipoMetaSubscriber : BaseSingleObserver<List<TipoMeta>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TipoMeta>) {
            val y = mapTipoMeta.parse(t)
            mView?.showTipoMeta(y)
        }
    }

    private inner class ListOtrasMarcasSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showOtrasMarcas(y)
        }
    }

    private inner class ConsultoraSubscriber : BaseSingleObserver<String>() {
        override fun onSuccess(t: String) {
            hideViewLoading()
            mView?.showConsultoraRecomienda(t)
        }

        override fun onError(exception: Throwable) {
            mView?.errorAlObtenerNombreConsultoraRecomendante()
        }
    }

    fun findConsultora(codigoConsultora: String) {
        obtenerNombreConsultoraRecomendanteUseCase.obtener(codigoConsultora, ConsultoraSubscriber())
    }

    fun listGeneros() {

        val generos = listOf(
            SexoModel().apply {
                codigo = Constant.LETTER_F
                descripcion = Constant.FEMENINO
            },
            SexoModel().apply {
                codigo = Constant.LETTER_M
                descripcion = Constant.MASCULINO
            }
        )

        mView?.showGeneros(generos)
    }

    fun listTipoMeta() {
        tipoMetaUseCase.obtener(ListTipoMetaSubscriber())
    }

    fun listNivelEducativo() {
        tablaLogicaUseCase.list(
            ListNivelEducativoSubscriber(),
            UneteTablaLogica.NIVELEDUCATIVO.value
        )
    }

    fun listEstadoCivil() {
        tablaLogicaUseCase.list(ListEstadoCivilSubscriber(), UneteTablaLogica.ESTADOCIVIL.value)
    }

    fun listTipoVinculoAval() {
        tablaLogicaUseCase.list(
            ListTipoVinculoAvalSubscriber(),
            UneteTablaLogica.TIPOVINCULO_AVAL.value
        )
    }

    fun listTipoVinculoFamiliar() {
        tablaLogicaUseCase.list(
            ListTipoVinculoFamiliarSubscriber(),
            UneteTablaLogica.TIPOVINCULO_FAMILIAR.value
        )
    }


    fun listTipoVinculoNoFamiliar() {
        tablaLogicaUseCase.list(
            ListTipoVinculoNoFamiliarSubscriber(),
            UneteTablaLogica.TIPOVINCULO_NOFAMILIAR.value
        )
    }

    fun listOtrasMarcas() {
        tablaLogicaUseCase.list(ListOtrasMarcasSubscriber(), UneteTablaLogica.OTRASMARCAS.value)
    }

    fun updatePostulante(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        showLoading()
        useCase.update(UpdatePostulanteSubscriber(), p)
    }

    fun listTipoPersona() {
        tablaLogicaUseCase.list(ListTipoPersonaSubscriber(), UneteTablaLogica.TIPOPERSONA.value)
    }

    fun listOrigenIngreso() {
        tablaLogicaUseCase.list(ListOrigenIngresoSubscriber(), UneteTablaLogica.ORIGENINGRESO.value)
    }

    fun validarZonaRiesgo(zonaSeccion: String) {
        parametroUneteUseCase.list(
            GetZonaRiesgoSubscriber(),
            UneteTipoParametro.NIVELRIESGO.tipo,
            zonaSeccion
        )
    }

    fun getCodigoRol(): String {
        return sesion.codigoRol
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
            sesion.codigoUsuario
        } else {
            Constant.EMPTY_STRING
        }
    }
}
