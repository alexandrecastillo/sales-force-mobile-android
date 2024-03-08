package biz.belcorp.salesforce.base.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.device.UpdateTokenUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.core.domain.usecases.traceability.TraceabilityUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.Metrics.CATEGORY_DBSIZE
import biz.belcorp.salesforce.core.utils.Metrics.OBJECTBOX_DBSIZE
import biz.belcorp.salesforce.core.utils.Metrics.measure
import biz.belcorp.salesforce.core.utils.io
import kotlinx.coroutines.launch

class MainViewModel(
    private val manageTopicsUseCase: ManageTopicsUseCase,
    private val fetchConfigUseCase: FetchRemoteConfigUseCase,
    private val traceabilityUseCase: TraceabilityUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val configUseCase: ConfigurationUseCase,
    private val mainMapper: MainMapper
) : ViewModel() {

    val viewState: LiveData<MainViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<MainViewState>()

    fun checkUpdate() {
        viewModelScope.launch(handler) {
            io {
                val config = configUseCase.getConfiguration()
                val model = mainMapper.map(config)
                _viewState.postValue(MainViewState.Success(model))
            }
        }
    }

    fun initSetup() {
        viewModelScope.launch(handler) {
            io {
                fetchConfigUseCase.fetchConfig()
                traceabilityUseCase.setUserKeys()
                measure(OBJECTBOX_DBSIZE, CATEGORY_DBSIZE, ObjectBox.sizeOnMB())
                manageTopicsUseCase.subscribeTopics()
            }
        }
    }

    fun fetchFcmToken() {
        viewModelScope.launch(handler) {
            io {
                updateTokenUseCase.syncIfNeeded()
            }
        }
    }

    fun logout() {
        viewModelScope.launch(handler) {
            io {
                logoutUseCase.logout()
                _viewState.postValue(MainViewState.LogoutSuccess)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
