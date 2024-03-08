package biz.belcorp.salesforce.modules.billing.features.billing.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class BillingViewModel(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val uaUseCase: UaInfoUseCase,
    private val uaMapper: UaInfoMapper
) : ViewModel() {

    private val session by lazy { sessionUseCase.obtener() }

    val uaViewState: LiveData<BillingViewState>
        get() = _uaViewState

    private val _uaViewState = MutableLiveData<BillingViewState>()

    fun getUaInformation() {
        viewModelScope.launch(handler) {
            io {
                val uas = uaUseCase.getAssociatedUaListByUaKey(session.llaveUA)
                val selectorModel = uaMapper.mapToSelector(uas)
                _uaViewState.postValue(BillingViewState.Success(selectorModel))
            }
        }
    }

    fun updateChildDescription(model: UaInfoModel) {
        if (model.isThirdPerson) {
            val uaName = uaMapper.map(model.label, role = model.role)
            _uaViewState.value =
                BillingViewState.ShowChildDescription(
                    uaName,
                    model.userKpiInformation,
                    model.isCovered
                )
        } else {
            _uaViewState.value = BillingViewState.HideChildDescription
        }
    }

    fun updateChildDetail(model: UaInfoModel) {
        _uaViewState.postValue(BillingViewState.UpdateItem(model.uaKey))
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            Log.e(BillingViewModel::javaClass.name, message)
        }
    }
}
