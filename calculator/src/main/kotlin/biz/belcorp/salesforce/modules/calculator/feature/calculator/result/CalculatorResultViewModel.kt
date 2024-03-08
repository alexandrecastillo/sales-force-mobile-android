package biz.belcorp.salesforce.modules.calculator.feature.calculator.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.lazyDeferred
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.CalculatorResultUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.PartnerVariableUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.UpperLevelUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.CalculatorResultViewState
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.mapper.CalculatorMapper
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel
import kotlinx.coroutines.launch

class CalculatorResultViewModel(
    private val getCalculatorResultUseCase: CalculatorResultUseCase,
    private val getSessionUseCase: ObtenerSesionUseCase,
    private val getConfigurationUseCase: ConfigurationUseCase,
    private val getCampaignUseCase: ObtenerCampaniasUseCase,
    private val getCalculatorVariablePartnerUseCase: PartnerVariableUseCase,
    private val getUpperLevelUseCase: UpperLevelUseCase,
    private val mapper: CalculatorMapper
) : ViewModel() {

    val viewState: LiveData<CalculatorResultViewState> get() = _viewState
    private val _viewState = MutableLiveData<CalculatorResultViewState>()
    private val session get() = requireNotNull(getSessionUseCase.obtener())
    private val currency by viewModelScope.lazyDeferred {
        getConfigurationUseCase.getConfiguration().currencySymbol
    }

    fun getPartnerVariable() {
        viewModelScope.launch(handler) {
            io {
                val request = getCalculatorVariablePartnerUseCase.getPartnerVariable()
                val params = CalculatorMapper.CalculatorMapperParams(currency.await(), getCampaignUseCase.obtenerCampaniaActual().nombreCorto.deleteHyphen(), request)
                val model = mapper.transformVariableSocia(params)
                _viewState.postValue(CalculatorResultViewState.Success(model))
            }
        }
    }

    fun saveResult(model: CalculatingResultModel) {
        model.codZona = session.zona
        model.codRegion = session.region
        model.codSeccion = session.seccion
        viewModelScope.launch(handler) {
            io {
                mapper.transformResultado(model)?.let { calculatorResult ->
                    getCalculatorResultUseCase.insert(calculatorResult)
                    _viewState.postValue(
                        CalculatorResultViewState.Inserted(model)
                    )
                }
            }
        }
    }

    fun newCalculation() {
        viewModelScope.launch(handler) {
            io {
                getCalculatorResultUseCase.delete()
                _viewState.postValue(
                    CalculatorResultViewState.Deleted
                )
            }
        }
    }

    companion object {
        const val IM_META_PEDIDO = "P"
        const val IM_META_VENTA = "V"
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value =
                CalculatorResultViewState.Failed(message)
        }
    }
}
