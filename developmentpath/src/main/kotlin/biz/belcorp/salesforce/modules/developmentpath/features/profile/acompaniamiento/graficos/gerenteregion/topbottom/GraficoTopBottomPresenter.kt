package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.ObtenerGraficoTopBottomUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaign
import biz.belcorp.salesforce.core.utils.uiThread

class GraficoTopBottomPresenter(
    private val view: GraficoTopBottomView,
    private val useCase: ObtenerGraficoTopBottomUseCase,
    val mapper: TopBottomMapper
) {

    fun obtener(personaId: Long, rol: Rol, tipoGrafico: TipoGrafico) {
        useCase.obtener(personaId, rol, tipoGrafico, GraficoTopBottomSubscriber())
    }

    inner class GraficoTopBottomSubscriber : BaseSingleObserver<TopBottomHistorico>() {

        override fun onSuccess(t: TopBottomHistorico) {
            doAsync {
                val tops = mapper.mapTop(t)
                val bottoms = mapper.mapBottom(t)
                uiThread {
                    view.pintarTops(tops, t.mostrarEnDosLineas)
                    view.pintarBottoms(bottoms, t.mostrarEnDosLineas)
                    pintarCampania(t)
                }
            }
        }
    }

    private fun pintarCampania(topBottomHistorico: TopBottomHistorico) {
        val campania = topBottomHistorico.campania.maskCampaign()
        when (topBottomHistorico.tipoGrafico) {
            TipoGrafico.RETENCION_6D6 -> view?.pintarCampaniaEnTitulo(campania)
            else -> view.pintarCampaniaEnTituloConComparativa(campania)
        }
    }
}
