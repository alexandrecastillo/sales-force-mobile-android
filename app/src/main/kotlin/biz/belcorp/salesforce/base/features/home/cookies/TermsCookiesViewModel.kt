package biz.belcorp.salesforce.base.features.home.cookies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class TermsCookiesViewModel(
    private val termsConditionsUseCase: TermConditionsUseCase
): ViewModel() {

    val viewState: LiveData<TermsCookiesViewState> get() = _viewState
    private val _viewState = MutableLiveData<TermsCookiesViewState>()

    fun saveTerms() {
        viewModelScope.launch(handler) {
            io {
                val request = termsConditionsUseCase.saveApprovedTerms(ApproveTermsParams.COOKIES)
                _viewState.postValue(TermsCookiesViewState.SuccessApproved(request))
            }
        }
    }

    fun getTermUrl(term: String) {
        viewModelScope.launch(handler) {
            io {
                val request = termsConditionsUseCase.getTerm(term)
                _viewState.postValue(TermsCookiesViewState.SuccessLink(request))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = TermsCookiesViewState.Failed(message)
        }
    }

}
