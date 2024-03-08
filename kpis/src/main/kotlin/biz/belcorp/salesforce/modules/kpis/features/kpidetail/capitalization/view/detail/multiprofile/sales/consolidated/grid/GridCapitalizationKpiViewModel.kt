package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.CapitalizationGridType
import kotlinx.coroutines.launch

class GridCapitalizationKpiViewModel(
    private val useCase: GridConsolidatedUseCase,
    private val mapper: GridCapitalizationMapper
) : ViewModel() {

    val viewState: LiveData<GridCapitalizationViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<GridCapitalizationViewState>()

    fun getGridData(@CapitalizationGridType filterType: String, ua: LlaveUA?) {
        viewModelScope.launch(handler) {
            io {
                val data = useCase.getCapitalizationInfo(ua)
                val gridData = mapper.map(data, filterType)
                _viewState.postValue(GridCapitalizationViewState.Success(gridData))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewState.value = GridCapitalizationViewState.NonDataAvailable
        }
    }

}
