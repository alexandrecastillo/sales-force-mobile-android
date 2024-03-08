package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.RankingGraphicModel

sealed class RankingChartsViewState {

    class Success(val data: RankingGraphicModel) : RankingChartsViewState()

    object Failed : RankingChartsViewState()

}
