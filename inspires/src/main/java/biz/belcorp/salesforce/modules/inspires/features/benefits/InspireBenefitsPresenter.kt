package biz.belcorp.salesforce.modules.inspires.features.benefits

import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper

class InspireBenefitsPresenter(
    private val useCaseIndicator: GetIndicatorUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireBenefitsView

    fun create(view: InspireBenefitsView) {
        this.view = view
    }

    override fun destroy() {
        useCaseIndicator.dispose()
    }

    fun loadValues() {
        useCaseIndicator.one(InspiraIndicatorSubscriber())
    }

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            view.showValues(mapper.transformIndicator(t))
        }
    }
}
