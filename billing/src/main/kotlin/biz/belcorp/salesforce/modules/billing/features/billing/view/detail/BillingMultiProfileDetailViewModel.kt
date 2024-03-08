package biz.belcorp.salesforce.modules.billing.features.billing.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.BillingMultiProfileDetailUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingMultiProfileDetailMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingRejectedOrdersMapper
import kotlinx.coroutines.launch

class BillingMultiProfileDetailViewModel(
    private val billingDetailUseCase: BillingMultiProfileDetailUseCase,
    private val rejectedOrdersUseCase: RejectedOrdersUseCase,
    private val billingDetailMapper: BillingMultiProfileDetailMapper,
    private val billingRejectedOrdersMapper: BillingRejectedOrdersMapper
) : ViewModel() {

    val viewState: LiveData<BillingMultiProfileDetailViewState>
        get() = _viewState

    val viewStateRejectedOrders: LiveData<RejectedOrdersViewState>
        get() = _viewStateRejectedOrders

    private val _viewState = MutableLiveData<BillingMultiProfileDetailViewState>()
    private val _viewStateRejectedOrders = MutableLiveData<RejectedOrdersViewState>()


    fun getBillingDetailAdvancement(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val billingData = billingDetailUseCase.getBillingDetailAdvancement(uaKey)
                val result = billingDetailMapper.map(billingData)
                _viewState.postValue(BillingMultiProfileDetailViewState.Success(result))
            }
        }
    }

    fun getRejectedOrders(uaKey: LlaveUA) {
        viewModelScope.launch(handlerRejectedOrders) {
            io {
                val rejectedOrdersData = rejectedOrdersUseCase.getRejectedOrders(uaKey)
                val result = billingRejectedOrdersMapper.map(rejectedOrdersData)
                val state = if (result.isNotEmpty()) {
                    RejectedOrdersViewState.Success(result)
                } else {
                    RejectedOrdersViewState.EmptyView
                }
                _viewStateRejectedOrders.postValue(state)
            }
        }
    }

    private val handlerRejectedOrders = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewStateRejectedOrders.value = RejectedOrdersViewState.Failure
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value =
                BillingMultiProfileDetailViewState.Failure(exception.message.orEmpty())
        }
    }
}
