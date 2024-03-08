package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.presenter

import biz.belcorp.ffvv.presentation.feature.rdd.perfil.acompaniamiento.informacionhistorica.contenedor.view.InformacionHistoricaRegionView
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.ObtenerTitulosGraficosGrUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model.GraficoResumenMapper


class InformacionHistoricaRegionPresenter(
    private val useCase: ObtenerTitulosGraficosGrUseCase,
    private val mapper: GraficoResumenMapper
) {

    private var view: InformacionHistoricaRegionView? = null

    fun establecerView(view: InformacionHistoricaRegionView) {
        this.view = view
    }

    fun recuperar(personaId: Long, rol: Rol) {
        useCase.obtener(personaId, rol, GraficosObserver())
    }

    private fun procesarRespuesta(graficos: List<GraficoGr>) {
        doAsync {
            val modelos = mapper.map(graficos)
            uiThread {
                view?.cargarGraficos(modelos)
            }
        }
    }

    inner class GraficosObserver : BaseSingleObserver<List<GraficoGr>>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: List<GraficoGr>) {
            procesarRespuesta(t)
        }
    }
}
