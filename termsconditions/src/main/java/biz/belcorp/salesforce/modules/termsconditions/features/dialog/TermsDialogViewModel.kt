package biz.belcorp.salesforce.modules.termsconditions.features.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class TermsDialogViewModel(
    private val syncTermsConditionsUseCase: SyncTermsConditionsUseCase,
    private val termsConditionsUseCase: TermConditionsUseCase
) : ViewModel() {

    val viewState: LiveData<TermsDialogViewState> get() = _viewState
    private val _viewState = MutableLiveData<TermsDialogViewState>()

    fun syncTerms(){
        viewModelScope.launch(handler) {
            io {
                val hasTerms = termsConditionsUseCase.hasTerms()
                if(!hasTerms) syncTermsConditionsUseCase.sync()
                _viewState.postValue(TermsDialogViewState.SuccessSync)
            }
        }
    }

    fun saveTerms() {
        viewModelScope.launch(handler) {
            io {
                val request = termsConditionsUseCase.saveApprovedTerms(ApproveTermsParams.LINK_SE)
                _viewState.postValue(TermsDialogViewState.Success(request))
            }
        }
    }

    fun saveTerms(terms: List<String>) {
        viewModelScope.launch(handler) {
            io {
                var request = false
                for (term in terms) {
                    request = termsConditionsUseCase.saveApprovedTerms(term)
                }
                _viewState.postValue(TermsDialogViewState.Success(request))
            }
        }
    }

    fun getTermUrl(term: String) {
        viewModelScope.launch(handler) {
            io {
                val request = termsConditionsUseCase.getTerm(term)
                _viewState.postValue(TermsDialogViewState.SuccessLink(request))
            }
        }
    }

    fun getUsername(){
        viewModelScope.launch(handler) {
            io {
                val name = termsConditionsUseCase.getUserName()
                _viewState.postValue(TermsDialogViewState.SuccessName(name))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = TermsDialogViewState.Failed(message)
        }
    }

}
