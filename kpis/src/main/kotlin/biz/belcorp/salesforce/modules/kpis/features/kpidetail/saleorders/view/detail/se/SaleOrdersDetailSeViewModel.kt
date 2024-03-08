package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.mapper.SaleOrderSeMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.MultiBrandViewState
import kotlinx.coroutines.launch

class SaleOrdersDetailSeViewModel(
    private val saleOrdersUseCase: SaleOrdersUseCase,
    private val saleOrderSeMapper: SaleOrderSeMapper
) : ViewModel() {

    val viewState: LiveData<SaleOrdersDetailSeViewState> get() = _viewState
    val multibrandViewState: LiveData<MultiBrandViewState> get() = _multibrandViewState

    private val _viewState = MutableLiveData<SaleOrdersDetailSeViewState>()
    private val _multibrandViewState = MutableLiveData<MultiBrandViewState>()

    fun getSaleOrder(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = saleOrdersUseCase.getSaleOrder(uaKey)
                val model = saleOrderSeMapper.map(data)
                _viewState.postValue(SaleOrdersDetailSeViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewState.postValue(SaleOrdersDetailSeViewState.Failed(message))
        }
    }
}
