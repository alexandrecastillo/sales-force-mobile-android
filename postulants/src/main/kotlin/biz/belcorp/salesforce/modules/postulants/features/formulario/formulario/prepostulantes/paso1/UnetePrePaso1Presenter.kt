package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PrePostulante
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.TablaLogicaUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.PostulantTextResolver
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.UnetePaso1BasePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TablaLogicaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.mappers.CampaignModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PrePostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant


class UnetePrePaso1Presenter(
    private val useCase: PostulantesUseCase,
    parametroUneteUseCase: ParametroUneteUseCase,
    tablaLogicaUseCase: TablaLogicaUseCase,
    mapParametroUnete: ParametroUneteModelDataMapper,
    mapTablaLogica: TablaLogicaModelDataMapper,
    private val mapPrePostulante: PrePostulantesModelDataMapper,
    private val obtenerCampaniasUseCase: ObtenerCampaniasUseCase,
    val mapperCampaign: CampaignModelDataMapper,
    useCaseSession: ObtenerSesionUseCase,
    errorMessageFactory: ErrorMessageFactory,
    textResolver: PostulantTextResolver
) : Presenter,
    UnetePaso1BasePresenter(
        useCase, mapParametroUnete, errorMessageFactory, mapTablaLogica,
        useCaseSession, tablaLogicaUseCase, parametroUneteUseCase, textResolver
    ) {

    var postulante: PrePostulante? = null
    var postulanteModel: PrePostulanteModel? = null
    var campanaActual: String = Constant.EMPTY_STRING

    override fun destroy() {
        super.destroyFather()
    }

    private fun created(prePostulante: PrePostulante) {
        mView?.created(mapPrePostulante.map(prePostulante))
    }

    fun recuperarRol(): String {
        return sesion.codigoRol
    }

    fun recuperarZona(): String? {
        return sesion.zona
    }

    fun recuperarSeccion(): String? {
        return sesion.seccion
    }

    fun obtenerCampanaActual() {
        return obtenerCampaniasUseCase.obtenerCampanias(ObtenerCampanaSubscriber())
    }

    private inner class ObtenerCampanaSubscriber : BaseSingleObserver<List<Campania>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<Campania>) {
            val list = mapperCampaign.map(t).take(2)
            campanaActual = list[0].codigo.let { it }
        }

    }

    private inner class UpdateOfflinePrePostulanteSubscriber : BaseSingleObserver<PrePostulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: PrePostulante) {
            hideViewLoading()
            created(t)
        }
    }

    fun updatePrePostulante(prePostulante: PrePostulanteModel) {
        updatePrePostulanteFinal(prePostulante)
    }

    fun validarDatos() {
        mView?.validarDatos()
    }

    private fun updatePrePostulanteFinal(prePostulante: PrePostulanteModel) {
        val p = mapPrePostulante.reverseMap(prePostulante)
        showLoading()
        useCase.updateOffline(UpdateOfflinePrePostulanteSubscriber(), p)
    }


    fun validarBloqueos(pais: String, documento: String, tipoDocumento: String, zona: String) {
        showLoading(Constant.EMPTY_STRING)
        useCase.validarBloqueosPaso1(
            ValidarBloqueosSubscriber(),
            pais, documento, tipoDocumento, zona
        )

    }

    override fun devolverDetallesBloqueos(bloqueos: BuroResponse) {
        mView?.createPreModel(bloqueos)
    }

}
