package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.detailbutton.GetDetailButtonUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.mapper.DetailButtonMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonType
import kotlinx.coroutines.launch

class DetailButtonViewModel(
    private val getDetailButtonUseCase: GetDetailButtonUseCase,
    private val getSessionUseCase: ObtenerSesionUseCase,
    private val mapper: DetailButtonMapper
) : ViewModel() {

    private val session by lazy { getSessionUseCase.obtener() }

    private val mViewState = MutableLiveData<DetailButtonViewState>()
    val viewState: LiveData<DetailButtonViewState>
        get() = mViewState

    fun enableButton(@KpiType kpiType: Int) {
        viewModelScope.launch(handler) {
            io {
                val (campaign, isBilling) = getDetailButtonUseCase.isBillingForDetailButton()
                val detailButtonInfo = mapper.map(campaign, isBilling, kpiType, session.rol)
                detailButtonInfo.apply {
                    if (type != DetailButtonType.NONE) mViewState.postValue(
                        DetailButtonViewState.Success(title, type)
                    )
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            mViewState.value = DetailButtonViewState.Error(message)
        }
    }
}

