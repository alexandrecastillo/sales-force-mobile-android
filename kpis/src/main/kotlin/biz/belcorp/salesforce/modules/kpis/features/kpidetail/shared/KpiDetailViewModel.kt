package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiDetailParamsUseCase
import kotlinx.coroutines.launch

class KpiDetailViewModel(
    private val kpiDetailParamsUseCase: KpiDetailParamsUseCase,
    private val mapper: KpiDetailMapper
) : ViewModel() {

    val viewState: LiveData<KpiDetailViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<KpiDetailViewState>()

    fun getParams(kpiType: Int) {
        viewModelScope.launch(handler) {
            io {
                val params = kpiDetailParamsUseCase.getParams(kpiType)
                _viewState.postValue(KpiDetailViewState.Success(mapper.map(params)))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = KpiDetailViewState.Error(message)
        }
    }
}
