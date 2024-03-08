package biz.belcorp.salesforce.modules.consultants.features.searchunified

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.utils.io
import kotlinx.coroutines.launch

class ConsultantViewModel(
    private val uaInfoUseCase: UaInfoUseCase,
    private val uaInfoMapper: UaInfoMapper,
    private val sessionUseCase: ObtenerSesionUseCase
) : ViewModel() {

    val viewState: LiveData<SearchConsultantViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<SearchConsultantViewState>()

    fun getSelectorUas() {
        viewModelScope.launch(handler) {
            io {
                val uas =
                    uaInfoUseCase.getAssociatedUaListByUaKey(
                        sessionUseCase.obtener().llaveUA,
                        excludeParent = true
                    )
                val models = uaInfoMapper.mapToSelector(uas)
                _viewState.postValue(SearchConsultantViewState.Success(models))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewState.postValue(
                SearchConsultantViewState.Failure(
                    message
                )
            )
        }
    }
}
