package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.GetResultsLastCampaignUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers.ResultLastCampaignMapper
import kotlinx.coroutines.launch

class ResultsLastCampaignViewModel(
    private val useCase: GetResultsLastCampaignUseCase,
    private val mapper: ResultLastCampaignMapper
) : ViewModel() {

    val viewState: LiveData<ResultsLastCampaignViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ResultsLastCampaignViewState>()

    fun getResults(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val results = useCase.getResults(personIdentifier)
                val data = mapper.map(results)
                _viewState.postValue(ResultsLastCampaignViewState.Success(data))
            }
        }
    }


    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = ResultsLastCampaignViewState.Failure }
    }
}
