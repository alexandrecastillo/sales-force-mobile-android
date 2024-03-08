package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.GraphicCapitalizationUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper.CapitalizationSeBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper.CapitalizationSeMapper
import kotlinx.coroutines.launch

class CapitalizationSeViewModel(
    private val useCase: GraphicCapitalizationUseCase,
    private val mapper: CapitalizationSeMapper,
    private val barEntryMapper: CapitalizationSeBarEntryMapper
) : ViewModel() {

    val viewState: LiveData<CapitalizationSeViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<CapitalizationSeViewState>()

    fun obtenerCapitalizacion(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val list = useCase.getGraphicCapitalization(personIdentifier)
                if (list.isNotEmpty()) {
                    val modelList = list.map { mapper.reverseMap(it) }
                    val barEntrySet = barEntryMapper.map(modelList)
                    val model = mapper.mapToModel(modelList, barEntrySet)
                    _viewState.postValue(CapitalizationSeViewState.Success(model))
                } else {
                    _viewState.postValue(CapitalizationSeViewState.Empty)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = CapitalizationSeViewState.Failure }
    }

}
