package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.UpperLevelUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.mapper.CalculatorMapper
import kotlinx.coroutines.launch

class UpperLevelViewModel(
    private val upperLevelUseCase: UpperLevelUseCase,
    private val calculatorMapper: CalculatorMapper
) : ViewModel() {

    val viewState: LiveData<UpperLevelViewState> get() = _viewState
    private val _viewState = MutableLiveData<UpperLevelViewState>()

    fun getUpperLevel() {
        viewModelScope.launch(handler) {
            io {
                val request = upperLevelUseCase.all()
                _viewState.postValue(
                    UpperLevelViewState.Success(
                        calculatorMapper.transformCalculadoraNivelSuperior(request)
                    )
                )

            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value =
                UpperLevelViewState.Failed(message)
        }
    }

}
