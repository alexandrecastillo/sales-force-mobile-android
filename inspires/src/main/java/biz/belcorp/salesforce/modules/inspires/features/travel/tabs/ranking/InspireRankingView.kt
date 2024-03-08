package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.ranking

import biz.belcorp.salesforce.modules.inspires.model.InspiraRankingModel

interface InspireRankingView {
    fun showValues(list: List<InspiraRankingModel>, topeRanking: Int?)
}
