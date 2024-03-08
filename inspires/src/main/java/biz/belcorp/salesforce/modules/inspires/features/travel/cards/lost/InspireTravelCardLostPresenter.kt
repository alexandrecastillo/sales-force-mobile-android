package biz.belcorp.salesforce.modules.inspires.features.travel.cards.lost

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper
import biz.belcorp.salesforce.modules.inspires.util.obtenerAnioCampania

class InspireTravelCardLostPresenter(
    private val useCaseIndicator: GetIndicatorUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireTravelCardLostView

    fun create(view: InspireTravelCardLostView) {
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
            mapper.transformIndicator(t).let {
                val nextYear = it.campania.toString().obtenerAnioCampania()?.plus(Constant.NUMBER_ONE)
                view.showValues(it, nextYear)
            }
        }
    }
}
