package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.lazyDeferred
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.LevelParameterUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.mapper.CalculatorMapper
import kotlinx.coroutines.launch

class ParameterByLevelViewModel (
    private val levelParameterUseCase: LevelParameterUseCase,
    private val calculatorMapper: CalculatorMapper,
    private val configurationRepository: ConfigurationUseCase
) : ViewModel() {

    private val currency by viewModelScope.lazyDeferred {
        configurationRepository.getConfiguration().currencySymbol
    }

    val viewState: LiveData<ParameterByLevelViewState> get() = _viewState
    private val _viewState = MutableLiveData<ParameterByLevelViewState>()

    fun getParameterByLevel(code: Int) {
        viewModelScope.launch(handler) {
            io {
                val request = levelParameterUseCase.parametroPorNivel(code)
                _viewState.postValue(
                    ParameterByLevelViewState.Success(
                        calculatorMapper.map(request, currency.await())
                    )
                )
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value =
                ParameterByLevelViewState.Failed(message)
        }
    }

}
