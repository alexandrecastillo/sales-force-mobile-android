package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.digital.core.domain.usecases.GetDigitalInfoUseCase
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid.DigitalConsolidatedMapper
import kotlinx.coroutines.launch

class DigitalConsolidatedViewModel(
    private val getDigitalInfoUseCase: GetDigitalInfoUseCase,
    private val consolidatedMapper: DigitalConsolidatedMapper
) : ViewModel() {

    val viewState: LiveData<DigitalConsolidatedViewState> get() = _viewState

    private val _viewState = MutableLiveData<DigitalConsolidatedViewState>()

    fun getGridData(@DigitalFilterType type: Int, uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val digitalData = getDigitalInfoUseCase.getDigitalConsolidated(uaKey)
                val columns = consolidatedMapper.map(type, digitalData)
                val viewState = DigitalConsolidatedViewState.Success(columns)
                ui { _viewState.value = viewState }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
