package biz.belcorp.salesforce.modules.billing.features.billing.view.header.se

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.GetBillingAdvancementUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.BillingMapper
import kotlinx.coroutines.launch

class BillingHeaderViewModel(
    private val useCase: GetBillingAdvancementUseCase,
    private val mapper: BillingMapper
) : ViewModel() {

    val viewState: LiveData<BillingHeaderViewState>
        get() = _viewState

    private var _viewState = MutableLiveData<BillingHeaderViewState>()

    fun getBillingAdvancement(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = useCase.getBillingAdvancement(uaKey)
                val mappedData = mapper.map(data)
                val state = BillingHeaderViewState.Success(mappedData)
                _viewState.postValue(state)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        val message = exception.message.orEmpty()
        Log.e(BillingHeaderViewModel::javaClass.name, message)
    }
}
