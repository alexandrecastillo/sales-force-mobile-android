package biz.belcorp.salesforce.modules.inspires.features.travel.cards.limited

import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper

class InspireTravelCardLimitedPresenter(
    private val useCaseIndicator: GetIndicatorUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireTravelCardLimitedView

    fun create(view: InspireTravelCardLimitedView) {
        this.view = view
    }

    fun loadValues() {
        useCaseIndicator.one(InspiraIndicatorSubscriber())
    }

    override fun destroy() {
        useCaseIndicator.dispose()
    }

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            view.showValues(mapper.transformIndicator(t))
        }
    }
}
