package biz.belcorp.salesforce.modules.consultants.features.quantity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetFilterParamsUseCase
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.FilterConsultantMapper
import biz.belcorp.salesforce.modules.consultants.features.quantity.ConsultantsQuantityViewState.*
import kotlinx.coroutines.launch

class ConsultantsQuantityViewModel(
    private val getConsultantsUseCase: GetConsultantsUseCase,
    private val syncConsultantsUseCase: SyncConsultantsUseCase,
    private val getFilterParamsUseCase: GetFilterParamsUseCase,
    private val filterMapper: FilterConsultantMapper
) : ViewModel() {

    val viewState: LiveData<ConsultantsQuantityViewState> get() = _viewState

    private val _viewState = MutableLiveData<ConsultantsQuantityViewState>()

    fun getConsultantsQuantity(consultantFilter: ConsultantFilter) =
        viewModelScope.launch(handler) {
            io {
                countConsultants(consultantFilter)
                syncConsultants {
                    countConsultants(consultantFilter)
                }
                ui { _viewState.value = HideLoading }
            }
        }

    private suspend fun countConsultants(consultantFilter: ConsultantFilter) {
        val extraFilterParams = getFilterParamsUseCase.getExtraParams()
        val filter = filterMapper.map(extraFilterParams, consultantFilter)
        val quantity = getConsultantsUseCase.getConsultantsQuantity(filter)
        val buttonEnabled = quantity > NUMERO_CERO
        _viewState.postValue(Success(quantity, buttonEnabled))
    }

    private suspend fun syncConsultants(onUpdated: suspend () -> Unit) {
        syncConsultantsUseCase.sync().let {
            if (it is SyncType.Updated) {
                onUpdated.invoke()
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.postValue(Failed(message))
        }
    }

}
