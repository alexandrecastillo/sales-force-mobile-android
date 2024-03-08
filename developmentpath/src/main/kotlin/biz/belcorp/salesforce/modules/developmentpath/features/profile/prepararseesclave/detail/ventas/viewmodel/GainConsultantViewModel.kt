package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetGainConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers.GainConsultantMapper
import kotlinx.coroutines.launch

class GainConsultantViewModel(
    private val gainConsultantUseCase: GetGainConsultantUseCase,
    private val gainConsultantMapper: GainConsultantMapper
) : ViewModel() {

    val viewState: LiveData<GainConsultantViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<GainConsultantViewState>()

    fun getGainConsultant(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val gainContainer = gainConsultantUseCase.getGainConsultant(personIdentifier.code)
            val model = gainConsultantMapper.map(gainContainer)
            _viewState.postValue(GainConsultantViewState.Success(model))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = GainConsultantViewState.Failure }
    }
}
