package biz.belcorp.salesforce.base.features.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class SplashViewModel(
    private val useCase: ObtenerSesionUseCase,
    private val termConditionsUseCase: TermConditionsUseCase
) : ViewModel() {

    val viewState: LiveData<SplashViewState> get() = _viewState
    private val _viewState = MutableLiveData<SplashViewState>()

    fun checkSession() {
        val authenticated = useCase.isAvailable()
        if (authenticated) {
            _viewState.value = SplashViewState.Authenticated
        } else {
            _viewState.value = SplashViewState.Unauthenticated
        }
    }

    fun checkTermApproved() {
        viewModelScope.launch(handler) {
            io {
                val termValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.TERM)
                val privacyValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.PRIVACY)
                _viewState.postValue(SplashViewState.TermValidated(termValidation && privacyValidation))
            }
        }
    }

    fun checkIfObjectBoxFailSchemaAndGetTermsApproved(){
        viewModelScope.launch(objectBoxErrorHandler) {
            io {
                termConditionsUseCase.syncApprovedTerms()
                //TODO Catch error when internet is off 

                val termValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.TERM)
                val privacyValidation = termConditionsUseCase.isTermApproved(ApproveTermsParams.PRIVACY)

                _viewState.postValue(SplashViewState.TermValidated(termValidation && privacyValidation))
            }
        }
    }

    fun isLogged(): Boolean = useCase.isAvailable()

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value = SplashViewState.Failure(exception.message.orEmpty())
        }
    }

    private val objectBoxErrorHandler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value = SplashViewState.Failure(exception.message.orEmpty())
        }
    }
}
