package biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.GetBillingAdvancementUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.BillingMultiProfileHeaderMapper
import kotlinx.coroutines.launch

class BillingMultiProfileHeaderViewModel(
    private val billingUseCase: GetBillingAdvancementUseCase,
    private val mapper: BillingMultiProfileHeaderMapper
) : ViewModel() {

    val viewState: LiveData<BillingMultiProfileHeaderViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<BillingMultiProfileHeaderViewState>()

    fun getBillingInformation(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val billing = billingUseCase.getBillingAdvancement(uaKey)
                val data = mapper.map(billing)
                _viewState.postValue(BillingMultiProfileHeaderViewState.Success(data))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value =
                BillingMultiProfileHeaderViewState.Failure(exception.message.orEmpty())
        }
    }
}
