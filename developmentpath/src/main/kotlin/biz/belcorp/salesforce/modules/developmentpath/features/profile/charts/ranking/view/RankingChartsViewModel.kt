package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking.GetRankingUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.mappers.RankingChartsMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RankingChartsViewModel(
    private val getRankingUseCase: GetRankingUseCase,
    private val rankingChartsMapper: RankingChartsMapper
) : ViewModel() {

    val viewState: LiveData<RankingChartsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<RankingChartsViewState>()

    fun getRankingInfo(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val info = getRankingUseCase.getRankingInfo(personIdentifier)
                val model = rankingChartsMapper.map(info)
                _viewState.postValue(RankingChartsViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = RankingChartsViewState.Failed }
    }

}
