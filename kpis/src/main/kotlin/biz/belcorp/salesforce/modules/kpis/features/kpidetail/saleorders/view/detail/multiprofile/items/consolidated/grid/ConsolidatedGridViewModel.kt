package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.GridConsolidatedMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import kotlinx.coroutines.launch

class ConsolidatedGridViewModel(
    private val gridConsolidatedUseCase: GridConsolidatedUseCase,
    private val gridConsolidatedMapper: GridConsolidatedMapper
) : ViewModel() {

    private val _viewState = MutableLiveData<ConsumableEvent>()

    val viewState: LiveData<ConsumableEvent>
        get() = _viewState

    fun getGridData(@SaleOrderGridType type: String, uaKey: LlaveUA? = null) {
        viewModelScope.launch(handler) {
            io {
                val data = gridConsolidatedUseCase.getConsolidatedInfo(uaKey)
                val gridModel = gridConsolidatedMapper.map(data, type)
                _viewState.postValue(consumeSuccessEvent(gridModel))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewState.value = consumeNonDataEvent()
        }
    }

    private fun consumeSuccessEvent(gridModel: List<Column>): ConsumableEvent {
        return ConsumableEvent(ConsolidatedGridViewState.Success(gridModel))
    }

    private fun consumeNonDataEvent(): ConsumableEvent {
        return ConsumableEvent(ConsolidatedGridViewState.NonDataAvailable)
    }
}
