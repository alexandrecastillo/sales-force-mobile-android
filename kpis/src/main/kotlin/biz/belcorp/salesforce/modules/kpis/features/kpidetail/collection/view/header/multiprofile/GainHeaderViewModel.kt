package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import kotlinx.coroutines.launch

class GainHeaderViewModel(
    private val getGainUseCase: GetGainUseCase,
    private val gainHeaderMapper: GainHeaderMapper
) : ViewModel() {

    val viewState: LiveData<GainHeaderViewState> get() = _viewState
    private val _viewState = MutableLiveData<GainHeaderViewState>()

    fun getGainHeaderInformation(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = getGainUseCase.getGainInformation(uaKey)
                val model = gainHeaderMapper.map(data)
                _viewState.postValue(GainHeaderViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value = GainHeaderViewState.Failed(exception.message.orEmpty())
        }
    }

}
