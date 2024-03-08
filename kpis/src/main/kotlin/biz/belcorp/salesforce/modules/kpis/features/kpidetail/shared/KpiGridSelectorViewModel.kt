package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiGridSelectorUseCase
import kotlinx.coroutines.launch

class KpiGridSelectorViewModel(
    private val useCase: KpiGridSelectorUseCase,
    private val mapper: KpiGridSelectorMapper
) : ViewModel() {

    val viewState: LiveData<KpiGridSelectorViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<KpiGridSelectorViewState>()

    fun getAdvancementLabel() {
        viewModelScope.launch(handler) {
            io {
                val pairedData = useCase.getRoleAndCampaign()
                val campaign = pairedData.second

                val label = mapper.getAdvancementLabel(
                    pairedData.first,
                    campaign?.shortNameOnly.orEmpty()
                )

                _viewState.postValue(KpiGridSelectorViewState.Success(label))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }
}
