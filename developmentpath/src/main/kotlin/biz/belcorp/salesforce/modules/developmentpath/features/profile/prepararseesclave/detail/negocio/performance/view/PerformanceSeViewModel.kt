package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.GetPerformanceSeUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.mapper.PerformanceModelMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class PerformanceSeViewModel(
    private val getPerformanceSeUseCase: GetPerformanceSeUseCase,
    private val performanceModelMapper: PerformanceModelMapper
) : ViewModel() {

    val viewState: LiveData<PerformanceSeViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<PerformanceSeViewState>()

    fun getPerformance(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val performance = getPerformanceSeUseCase.getPerformance(personIdentifier)
                val model = performanceModelMapper.map(performance)
                _viewState.postValue(PerformanceSeViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

}
