package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.ActivesRetentionUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers.RetencionBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers.RetencionMapper
import kotlinx.coroutines.launch

class ActivesRetentionViewModel(
    private val useCase: ActivesRetentionUseCase,
    private val mapper: RetencionMapper,
    private val barEntryMapper: RetencionBarEntryMapper
) : ViewModel() {

    val viewState: LiveData<ActivesRetentionViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ActivesRetentionViewState>()

    fun getActivesRetention(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val list = useCase.getGraphicActives(personIdentifier)
                if (list.isNotEmpty()) {
                    val modelList = list.map { mapper.reverseMap(it) }
                    val barEntrySet = barEntryMapper.map(modelList)
                    val model = mapper.mapToModel(modelList, barEntrySet)
                    _viewState.postValue(ActivesRetentionViewState.Success(model))
                } else {
                    _viewState.postValue(ActivesRetentionViewState.Empty)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = ActivesRetentionViewState.Failure }
    }

}
