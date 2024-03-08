package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import kotlinx.coroutines.launch

class GainConsolidatedGridViewModel(
    private val getGainUseCase: GetGainUseCase,
    private val consolidatedMapper: GainConsolidatedMapper
) : ViewModel() {

    val viewState: LiveData<ConsumableEvent> get() = _viewState

    private val _viewState = MutableLiveData<ConsumableEvent>()

    fun getGridData(ua: LlaveUA,days: String) {
        viewModelScope.launch(handler) {
            io {
                val gainData = getGainUseCase.getUaData(ua,days)
                val columns = consolidatedMapper.map(gainData)
                val viewState = GainConsolidatedGridViewState.Success(columns)
                _viewState.postValue(ConsumableEvent(viewState))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewState.value = ConsumableEvent(GainConsolidatedGridViewState.NonDataAvailable)
        }
    }
}
