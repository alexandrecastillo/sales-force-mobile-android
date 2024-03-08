package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class TermsConditionsViewModel(
    private val termConditionsUseCase: TermConditionsUseCase,
    private val mapper: TermsConditionsMapper
) : ViewModel() {

    val viewState: LiveData<TermsConditionsViewState> get() = _viewState
    private val _viewState = MutableLiveData<TermsConditionsViewState>()

    fun getPoliticsTermsConditions() {
        viewModelScope.launch(handler) {
            io {
                val request = termConditionsUseCase.getTerms()
                _viewState.postValue(TermsConditionsViewState.Success(mapper.map(request)))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = TermsConditionsViewState.Failed(message)
        }
    }
}
