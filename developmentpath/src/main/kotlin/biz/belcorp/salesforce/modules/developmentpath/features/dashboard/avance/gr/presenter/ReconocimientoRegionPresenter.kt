package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.GetDesarrolloHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.ObtenerSesionPersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.DesarrolloHabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades.DesarrolloUaHabilidadesView

class ReconocimientoRegionPresenter(
    private var view: DesarrolloUaHabilidadesView?,
    private val sesionUseCase: ObtenerSesionPersonaUseCase,
    private val desarrolloHabilidadesUseCase: GetDesarrolloHabilidadesUseCase,
    private val desarrolloHabilidadMapper: DesarrolloHabilidadMapper
) {

    val sesion get() = sesionUseCase.obtener()

    fun obtenerDatos(planId: Long) {
        desarrolloHabilidadesUseCase.getDesarrolloHabilidades(
            planId,
            DesarrolloHabilidadesSubscriber()
        )
    }

    fun detachView() {
        view = null
    }

    private fun obtenerTituloDeUaPorRol(rol: Rol): String {
        return when (rol) {
            Rol.DIRECTOR_VENTAS -> "HABILIDADES GR"
            else -> "DE ${rol.uaBajoCargo()}"
        }
    }

    private fun procesarResponse(response: GetDesarrolloHabilidadesUseCase.Response) {
        doAsync {
            val list = desarrolloHabilidadMapper.parseList(response.habilidades)
            val periodoCampania =
                "${response.campaniaActual.periodo?.nombre()?.toUpperCase()} ${response.campaniaActual.nombreCorto}"
            val tituloPorUa = obtenerTituloDeUaPorRol(response.rol)
            val uaCampaniaAnterior = "$tituloPorUa ${response.campaniaAnterior.nombreCorto}"

            uiThread {
                view?.showTitle(uaCampaniaAnterior)
                view?.showCurrentCampaign(periodoCampania)
                view?.showDesarrolloRegion(list)
            }
        }
    }

    inner class DesarrolloHabilidadesSubscriber :
        BaseSingleObserver<GetDesarrolloHabilidadesUseCase.Response>() {

        override fun onSuccess(t: GetDesarrolloHabilidadesUseCase.Response) {
            procesarResponse(t)
        }
    }
}
