package biz.belcorp.salesforce.modules.brightpath.features.header

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.BrightPathArgs
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.IndicatorArgs
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.BrightPathIndicatorUseCase

class BrightPathHeaderKpiPresenter(private val useCase: BrightPathIndicatorUseCase) : BasePresenter() {

    private var view: BrightPathHeaderKpiView? = null

    fun create(view: BrightPathHeaderKpiView) {
        this.view = view
    }

    override fun onDestroy() {
        super.onDestroy()
        view = null
    }

    fun getSeData() {
        doAsync {
            val iData = useCase.getIndicatorDataAsync()
            uiThread {
                manageLevelIndicatorData(iData)
            }
        }
    }

    private fun manageLevelIndicatorData(i: IndicatorArgs) {
        when (i) {
            is BrightPathArgs.Se -> showSeData(i)
        }
    }


    private fun showSeData(indicator: BrightPathArgs.Se) {
        view?.showHeader(indicator.campaniaAnterior)
        view?.showTitle(indicator.acumuladoPorNivel)

        val datosCampaniaAnterior = indicator.totalCampaniaAnterior.drop(1).dropLast(1).split(",").toTypedArray()
        view?.showPrevCampaignValues(datosCampaniaAnterior)
        view?.showPrevCampaignText(indicator.campaniaAnterior)

        val datosAcumuladoPeriodo = indicator.acumuladoPeriodo.drop(1).dropLast(1).split(",").toTypedArray()
        view?.showCurrentPeriodText(indicator.periodoActual.split(".")[1])
        view?.showCurrentPeriodValues(datosAcumuladoPeriodo)
    }

}
