package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.UneteConfiguracionUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.FormularioView
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.UneteConfiguracionModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class UnetePresenter(
    private val useCase: PostulantesUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val uneteConfiguracionUseCase: UneteConfiguracionUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val useCaseParametroUnete: ParametroUneteUseCase,
    private val mapperConfig: UneteConfiguracionModelDataMapper,
    private val mapPostulante: PostulantesModelDataMapper
) : Presenter {

    private val sesion get() = obtenerSesionUseCase.obtener()
    var mView: FormularioView? = null

    fun setView(vw: FormularioView) {
        this.mView = vw
    }

    private inner class ObtenerPostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onSuccess(t: Postulante) {
            val model = mapPostulante.map(t)
            mView?.postulante(model)
            initStepper(model)
        }
    }

    private inner class ObtenerUneteConfiguracionSubscriber : BaseSingleObserver<Configuracion>() {

        override fun onError(exception: Throwable) {
            mView?.uneteConfiguracion(UneteConfiguracionModel())
        }

        override fun onSuccess(t: Configuracion) {
            mView?.uneteConfiguracion(mapperConfig.map(t))
        }
    }

    fun obtenerPostulante(uuid: String?, id: Int) {

        if (uuid == null) {

            val model = PostulanteModel()

            model.pais = sesion.countryIso
            model.codigoZona = sesion.zona
            model.paso = Constant.NUMERO_CERO

            mView?.postulante(model)

            initStepper(model)

        } else {
            useCase.get(ObtenerPostulanteSubscriber(), uuid, id)
        }
    }

    fun initStepper(model: PostulanteModel) {

        val pasos = UneteConfig.find(model.pais.orEmpty())?.pasos ?: Constant.NUMERO_CERO

        var currentStepPosition = when {
            model.solicitudPostulanteID == Constant.NUMERO_CERO && model.paso == Constant.NUMERO_UNO -> Constant.NUMERO_CERO
            model.pais!! in PaisUnete.paisesBuro && model.estadoPostulante == UneteEstado.GESTION_SAC.estado.toString() -> 1
            model.paso == pasos -> model.paso - 1
            else -> model.paso
        }

        if (isSeguroSocialEmpty(model)) {
            currentStepPosition = Constant.NUMERO_CERO
        }

        mView?.initStepper(pasos, currentStepPosition)

    }

    fun getConfiguracionUnete() {
        val codigoPais = sesion.countryIso
        val codigoRol = sesion.codigoRol
        uneteConfiguracionUseCase.get(ObtenerUneteConfiguracionSubscriber(), codigoPais, codigoRol)
    }

    fun listSMSZonas(zona: String?) {
        var zonaSeccion = getZona(zona)
        if (sesion.seccion != null) zonaSeccion += sesion.seccion
        zonaSeccion?.let {
            useCaseParametroUnete.listZonasSMS(
                ZonasSMSSubscriber(), UneteTipoParametro.ZONASMS.tipo, it
            )
        }
    }

    fun listZonasPagoContado(zona: String?) {
        useCaseParametroUnete.getParametroUnete(
            PagoContadoSubscriber(), UneteTipoParametro.ZONAPAGOCONTADO.tipo, getZona(zona).orEmpty()
        )
    }

    private fun getZona(zona: String?): String? {
        return when(sesion.rol) {
            Rol.GERENTE_REGION -> zona
            else -> sesion.zona
        }
    }

    fun getPais() = sesion.pais?.codigoIso.orEmpty()

    private inner class ZonasSMSSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            mView?.validarZonaSMS(Constant.SIN_ZONA)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            if (y.isNotEmpty()) {
                if (y.first().descripcion.isNullOrBlank())
                    mView?.validarZonaSMS(Constant.SIN_BLOQUEO)
                else
                    y.first().descripcion?.let {
                        mView?.validarZonaSMS(it.toInt())
                    }

            } else {
                mView?.validarZonaSMS(Constant.SIN_ZONA)
            }
        }
    }

    private inner class PagoContadoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {

            val y = mapParametroUnete.map(t)
            if (y.isNotEmpty()) {
                mView?.validarPagoContado()
            }
        }
    }

    private fun isSeguroSocialEmpty(model: PostulanteModel): Boolean {
        return (model.pais.equals(Pais.PUERTORICO.codigoIso)
            && model.numeroDocumento.isNullOrEmpty()
            && model.fuenteIngreso.equals(FuenteIngreso.UB))
    }

}
