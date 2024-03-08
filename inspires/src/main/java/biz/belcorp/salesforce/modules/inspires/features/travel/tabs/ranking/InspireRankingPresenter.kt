package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.ranking

import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraRankingUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper

class InspireRankingPresenter
constructor(
    private val getInspiraRankingUseCase: GetInspiraRankingUseCase,
    private val getUseCaseIndicator: GetIndicatorUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireRankingView

    fun create(view: InspireRankingView) {
        this.view = view
    }

    fun onPrepare() {
        getUseCaseIndicator.one(InspiraIndicatorSubscriber())
    }

    override fun destroy() {
        getUseCaseIndicator.dispose()
        getInspiraRankingUseCase.dispose()
    }

    private inner class InspiraRankingSubscriber(private val topeRanking: Int) : BaseSingleObserver<List<InspiraRanking>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<InspiraRanking>) {
            view.showValues(mapper.transformRanking(t), topeRanking)
        }
    }

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            getInspiraRankingUseCase.all(InspiraRankingSubscriber(mapper.transformIndicator(t).topeRanking))
        }
    }
}
