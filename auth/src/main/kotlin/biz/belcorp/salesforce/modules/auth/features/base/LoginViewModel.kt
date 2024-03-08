package biz.belcorp.salesforce.modules.auth.features.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.sync.exceptions.LoginSyncException
import biz.belcorp.salesforce.core.sync.groups.LoginSyncGroupManager
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginType
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


abstract class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val featuresUseCase: GetFeaturesUseCase,
    private val termConditionsUseCase: TermConditionsUseCase,
    private val syncGroupManager: LoginSyncGroupManager
) : ViewModel() {

    val viewState: LiveData<LoginViewState> get() = _viewState
    protected val _viewState = MutableLiveData<LoginViewState>()
    protected var loginType: LoginType? = null
    protected var isLoginSupport = false

    fun loadModules() {
        viewModelScope.launch(handler) {
            val modules = featuresUseCase.getFeaturesByRole()
            _viewState.postValue(LoginViewState.ReadyToInstall(modules))
        }
    }

    fun startSync() {
        if (loginType == LoginType.ONLINE) {
            viewModelScope.launch(handler) {
                io {
                    syncGroupManager.isLoginSupport = isLoginSupport
                    syncGroupManager.start()
                    loginUseCase.loginSuccessful()
                }
                _viewState.value = LoginViewState.SyncSuccess
            }
        } else {
            _viewState.value = LoginViewState.SyncSuccess
        }
    }

    fun checkTermApproved() {
        viewModelScope.launch(handler) {
            io {
                val termValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.TERM)
                val privacyValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.PRIVACY)
                _viewState.postValue(LoginViewState.TermValidated(termValidation && privacyValidation))
            }
        }
    }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            Logger.e(exception)
            loginUseCase.loginFailure()
            _viewState.value = when (exception) {
                is UnauthorizedException -> {
                    LoginViewState.LoginAuthError(exception.message.orEmpty())
                }
                is LoginSyncException -> {
                    LoginViewState.LoginError(exception.cause?.message.orEmpty())
                }
                else -> {
                    LoginViewState.LoginError(exception.message.orEmpty())
                }
            }
        }
    }

}
