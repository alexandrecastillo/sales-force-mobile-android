package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleGridMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.NewCycleGridType
import kotlinx.coroutines.launch

class NewCycleGridViewModel(
    private val getNewCycleIndicatorUseCase: GetNewCycleIndicatorUseCase,
    private val uaUseCase: UaInfoUseCase,
    private val newCycleGridMapper: NewCycleGridMapper,
    private val sessionUseCase: ObtenerSesionUseCase
) : ViewModel() {

    private val session by lazy { sessionUseCase.obtener() }
    val viewState: LiveData<ConsumableEvent> get() = _viewState

    private val _viewState = MutableLiveData<ConsumableEvent>()

    fun getGridData(@NewCycleGridType typeId: String, uaKey: LlaveUA? = null) {
        viewModelScope.launch(handler) {
            io {
                val columns = retrieveGridColumns(typeId, uaKey ?: session.llaveUA)
                _viewState.postValue(consumeSuccessEvent(columns))
            }
        }
    }

    private suspend fun retrieveGridColumns(
        @NewCycleGridType typeId: String,
        uaKey: LlaveUA
    ): List<Column> {
        val uaList = uaUseCase.getAssociatedUaListByUaKey(uaKey)
        return fetchGridData(typeId, uaKey, uaList)
    }

    private suspend fun fetchGridData(
        @NewCycleGridType typeId: String,
        uaKey: LlaveUA,
        uaList: List<UaInfo>
    ): List<Column> {
        val indicatorList = getNewCycleIndicatorUseCase.getNewCycleGridData(uaKey)
        return newCycleGridMapper.map(indicatorList, uaList, typeId)
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewState.value = consumeNonDataEvent()
        }
    }

    private fun consumeSuccessEvent(gridModel: List<Column>): ConsumableEvent {
        return ConsumableEvent(NewCycleGridViewState.Success(gridModel))
    }

    private fun consumeNonDataEvent(): ConsumableEvent {
        return ConsumableEvent(NewCycleGridViewState.NonDataAvailable)
    }
}
