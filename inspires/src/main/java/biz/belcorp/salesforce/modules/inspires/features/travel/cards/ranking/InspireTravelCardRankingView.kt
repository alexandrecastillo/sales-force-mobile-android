package biz.belcorp.salesforce.modules.inspires.features.travel.cards.ranking

import biz.belcorp.salesforce.modules.inspires.model.InspiraRankingModel
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel

interface InspireTravelCardRankingView {
    fun showIndicatorValues(model: InspireIndicatorModel)
    fun showRankingPosition(model: InspiraRankingModel)
}
