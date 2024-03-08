package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersDetailUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.SaleOrdersDetailMapper
import kotlinx.coroutines.launch

class SaleOrdersDetailViewModel(
    private val saleOrdersDetailUseCase: SaleOrdersDetailUseCase,
    private val mapper: SaleOrdersDetailMapper
) : ViewModel() {

    val viewState: LiveData<SaleOrdersDetailViewState> get() = _viewState

    private val _viewState = MutableLiveData<SaleOrdersDetailViewState>()

    fun getInfo() {
        viewModelScope.launch(handler) {
            io {
                val model = mapper.map(saleOrdersDetailUseCase.getInfo())
                _viewState.postValue(SaleOrdersDetailViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = SaleOrdersDetailViewState.Failed(message)
        }
    }
}
