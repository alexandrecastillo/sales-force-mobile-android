package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PrePostulante
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstado
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.UneteConfiguracionUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.FormularioPreView
import biz.belcorp.salesforce.modules.postulants.features.mapper.PrePostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.UneteConfiguracionModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant


class UnetePrePresenter(
    private val useCase: PostulantesUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val uneteConfiguracionUseCase: UneteConfiguracionUseCase,
    private val mapperConfig: UneteConfiguracionModelDataMapper,
    private val mapPrePostulante: PrePostulantesModelDataMapper
) : Presenter {

    private val sesion get() = obtenerSesionUseCase.obtener()
    private var mPreView: FormularioPreView? = null

    fun setPreView(vw: FormularioPreView) {
        this.mPreView = vw
    }

    fun getCountry(): String = sesion.countryIso

    fun getPais() = sesion.pais?.codigoIso.orEmpty()

    private inner class ObtenerPrePostulanteSubscriber : BaseSingleObserver<PrePostulante>() {

        override fun onSuccess(t: PrePostulante) {
            val model = mapPrePostulante.map(t)
            mPreView?.prePostulante(model)
            initPreStepper(model)
        }
    }

    private inner class ObtenerUneteConfiguracionSubscriber : BaseSingleObserver<Configuracion>() {

        override fun onError(exception: Throwable) {
            mPreView?.uneteConfiguracion(UneteConfiguracionModel())
        }

        override fun onSuccess(t: Configuracion) {
            mPreView?.uneteConfiguracion(mapperConfig.map(t))
        }
    }

    fun obtenerPrePostulante(uuid: String?, id: Int) {
        if (uuid == null) {
            val model = PrePostulanteModel()
            model.pais = sesion.countryIso
            model.codigoZona = sesion.zona
            model.paso = Constant.NUMERO_CERO

            mPreView?.prePostulante(model)

            initPreStepper(model)

        } else {
            useCase.getPre(ObtenerPrePostulanteSubscriber(), uuid, id)
        }
    }

    fun initPreStepper(model: PrePostulanteModel) {

        val pasos = Constant.NUMERO_DOS

        val currentStepPosition = when {
            model.solicitudPrePostulanteID == Constant.NUMERO_CERO && model.paso == 1 -> Constant.NUMERO_CERO
            model.estadoPostulante == UneteEstado.GESTION_SAC.estado.toString() -> Constant.NUMERO_UNO
            model.paso == pasos -> model.paso - 1
            else -> model.paso
        }
        mPreView?.initStepper(pasos, currentStepPosition)
    }

    fun getConfiguracionUnete() {
        val codigoPais = sesion.countryIso
        val codigoRol = sesion.codigoRol
        uneteConfiguracionUseCase.get(ObtenerUneteConfiguracionSubscriber(), codigoPais, codigoRol)
    }

}
