package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions

import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondiciones
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraCondicionesLeyendaUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraCondicionesUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel

class InspireConditionsPresenter
constructor(
    private val getUseCaseCondiciones: GetInspiraCondicionesUseCase,
    private val getUseCaseCondicionesLeyenda: GetInspiraCondicionesLeyendaUseCase,
    private val getUseCaseIndicator: GetIndicatorUseCase,
    private val mapper: InspireModelDataMapper

) : Presenter {

    private lateinit var view: InspireConditionsView

    fun create(view: InspireConditionsView) {
        this.view = view
    }

    fun onPrepare() {
        getUseCaseIndicator.one(InspiraIndicatorSubscriber())
        getUseCaseCondiciones.all(CondicionesObserver())
    }

    private fun showViewValues(model: InspireIndicatorModel) {
        view.showHeaderMessage(model.activa, model.nombreSE)
        view.showHeadeIcon(model.activa)
        if (model.activa.not()) {
            view.hideRecommendatioonTitle()
        } else {
            getUseCaseCondicionesLeyenda.all(CondicionesLeyendaObserver())
        }
    }

    override fun destroy() {
        getUseCaseIndicator.dispose()
        getUseCaseCondiciones.dispose()
        getUseCaseCondicionesLeyenda.dispose()
    }


    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            showViewValues(mapper.transformIndicator(t))
        }
    }

    private inner class CondicionesObserver : BaseSingleObserver<List<InspiraCondiciones>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<InspiraCondiciones>) {
            view.showConditionsList(mapper.transformConditions(t))
        }
    }

    private inner class CondicionesLeyendaObserver : BaseSingleObserver<GetInspiraCondicionesLeyendaUseCase.Response>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: GetInspiraCondicionesLeyendaUseCase.Response) {
            doAsync {
                val leyenda = mapper.transformConditionsLegend(t.list)
                val detalle = mapper.transformConditionsLegendDetail(t.listDetail)
                uiThread {
                    if (leyenda.isNullOrEmpty())
                        view.hideLegendTitle()
                    else
                        view.showDetail(leyenda, detalle)
                }
            }
        }
    }
}
