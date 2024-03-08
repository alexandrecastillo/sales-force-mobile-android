package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel

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
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import kotlinx.coroutines.launch

class UaDetailViewModel(
    private val uaUseCase: UaInfoUseCase,
    private val sessionUseCase: ObtenerSesionUseCase,
    private val uaInfoMapper: UaInfoMapper
) : ViewModel() {

    private val session get() = sessionUseCase.obtener()

    val uaViewState: LiveData<UaDetailViewState>
        get() = _uaViewState

    private val _uaViewState = MutableLiveData<UaDetailViewState>()

    fun getUaInformation() {
        viewModelScope.launch(handler) {
            io {
                val uas =
                    uaUseCase.getAssociatedUaListByUaKey(session.llaveUA, excludeParent = true)
                val selectorModel = uaInfoMapper.mapToSelector(uas)
                _uaViewState.postValue(
                    UaDetailViewState.Success(
                        selectorModel
                    )
                )
            }
        }
    }

    fun updateChildDescription(model: UaInfoModel) {
        val uaName = uaInfoMapper.map(model.label, role = model.role)
        _uaViewState.value =
            UaDetailViewState.ShowChildDescription(
                uaName,
                model.userKpiInformation,
                model.isCovered
            )
    }

    fun updateChildDetail(model: UaInfoModel) {
        _uaViewState.postValue(UaDetailViewState.UpdateItem(model.uaKey))
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _uaViewState.value =
                UaDetailViewState.Failure(
                    exception.message.orEmpty()
                )
        }
    }

}
