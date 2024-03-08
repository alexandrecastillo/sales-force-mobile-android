package biz.belcorp.salesforce.modules.inspires.features.travel.cards.ranking

import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraRankingUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper

class InspireTravelCardRankingPresenter(
    private val useCaseIndicator: GetIndicatorUseCase,
    private val useCaseRanking: GetInspiraRankingUseCase,
    private val mapper: InspireModelDataMapper

) : Presenter {

    lateinit var view: InspireTravelCardRankingView

    fun create(view: InspireTravelCardRankingView) {
        this.view = view
    }

    override fun destroy() {
        useCaseIndicator.dispose()
        useCaseRanking.dispose()
    }

    fun loadValues() {
        useCaseRanking.oneWhenIsUser(InspiraRankinkSubscriber())
        useCaseIndicator.one(InspiraIndicatorSubscriber())
    }

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            mapper.transformIndicator(t).let {
                view.showIndicatorValues(it)
            }
        }
    }

    private inner class InspiraRankinkSubscriber : BaseSingleObserver<InspiraRanking>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraRanking) {
            mapper.transformRanking(t).let {
                view.showRankingPosition(it)
            }
        }
    }
}
