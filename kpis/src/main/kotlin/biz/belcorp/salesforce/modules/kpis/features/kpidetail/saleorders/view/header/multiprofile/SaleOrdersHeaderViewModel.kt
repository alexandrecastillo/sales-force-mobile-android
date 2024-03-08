package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.mapper.SaleOrderMapper
import kotlinx.coroutines.launch

class SaleOrdersHeaderViewModel(
    private val saleOrdersUseCase: SaleOrdersUseCase,
    private val saleOrderMapper: SaleOrderMapper
) : ViewModel() {

    private val _viewState = MutableLiveData<SaleOrdersHeaderViewState>()
    private val _multiBrandViewState = MutableLiveData<MultiBrandViewState>()

    val viewState: LiveData<SaleOrdersHeaderViewState>
        get() = _viewState

    val multiBrandState: LiveData<MultiBrandViewState>
        get() = _multiBrandViewState

    fun getSaleHeaderOrders(uaKey: LlaveUA? = null) {
        viewModelScope.launch(handler) {
            io {

                val data = saleOrdersUseCase.getSaleOrderMultiProfile(uaKey)
                val model = saleOrderMapper.map(data)

                _viewState.postValue(SaleOrdersHeaderViewState.Success(model))
                _multiBrandViewState.postValue(
                    MultiBrandViewState.Success(
                        model
                    )
                )
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = SaleOrdersHeaderViewState.Failed(message)
        }
    }
}
