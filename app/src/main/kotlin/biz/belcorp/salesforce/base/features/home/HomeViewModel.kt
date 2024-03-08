package biz.belcorp.salesforce.base.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.features.uainfo.UaInfoViewState
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import biz.belcorp.salesforce.messaging.features.list.view.BaseNotificationViewState
import biz.belcorp.salesforce.messaging.features.list.view.NotificationPendingViewState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val uaUseCase: UaInfoUseCase,
    private val termsConditionsUseCase: TermConditionsUseCase,
    private val homeMapper: HomeMapper,
    private val uaInfoMapper: UaInfoMapper) : ViewModel() {

    val session get() = sessionUseCase.obtener()

    val viewState: LiveData<HomeViewState> get() = _viewState
    val uaInfoViewState: LiveData<UaInfoViewState> get() = _uaViewState
    val notificationViewState: LiveData<BaseNotificationViewState> get() = _notificationViewState
    val tersmViewState: LiveData<HomeViewState> get() = _tersmViewState

    private val _viewState = MutableLiveData<HomeViewState>()
    private val _uaViewState = MutableLiveData<UaInfoViewState>()
    private val _notificationViewState = MutableLiveData<BaseNotificationViewState>()
    private val _tersmViewState = MutableLiveData<HomeViewState>()

    fun hasPendingNotifications() {
        viewModelScope.launch(notificationHandler) {
            io {
                val hasPendingNotifications = getNotificationUseCase.getPendingNotifications()
                _notificationViewState.postValue(
                    NotificationPendingViewState.Success(hasPendingNotifications)
                )
            }
        }
    }

    fun getPeriodInformation() {
        viewModelScope.launch(periodHandler) {
            io {
                val model = homeMapper.map(session)
                _viewState.postValue(HomeViewState.Success(model))
            }
        }
    }

    fun getUaInformation() {
        viewModelScope.launch(uaHandler) {
            io {
                val uas = uaUseCase.getAssociatedUaListByUaKey(session.llaveUA)
                val selectorModel = uaInfoMapper.mapToSelector(uas)
                _uaViewState.postValue(UaInfoViewState.Success(selectorModel))
            }
        }
    }

    fun checkTermApproved() {
        viewModelScope.launch(handler) {
            io {
                val termValidation =
                    termsConditionsUseCase.isTermApproved(ApproveTermsParams.COOKIES)
                _tersmViewState.postValue(HomeViewState.TermValidated(termValidation))
            }
        }
    }

    private val uaHandler = CoroutineExceptionHandler { _, exception ->
        ui {
            _uaViewState.value = UaInfoViewState.Failure(exception.message.orEmpty())
        }
    }

    private val periodHandler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value = HomeViewState.Failure(exception.message.orEmpty())
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

    private val notificationHandler = CoroutineExceptionHandler { _, exception ->
        ui {
            _notificationViewState.value =
                NotificationPendingViewState.Failure(exception.message.orEmpty())
        }
    }

}
