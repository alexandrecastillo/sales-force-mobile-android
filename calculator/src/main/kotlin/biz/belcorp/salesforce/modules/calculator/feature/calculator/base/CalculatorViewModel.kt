package biz.belcorp.salesforce.modules.calculator.feature.calculator.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.CalculatorResultUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.calculatingresult.mapper.CalculatingResultMapper
import biz.belcorp.salesforce.modules.calculator.util.Constant
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val getCampaignUseCase: ObtenerCampaniasUseCase,
    private val getSesionUseCase: ObtenerSesionUseCase,
    private val calculatorResultUseCase: CalculatorResultUseCase,
    private val calculatingResultMapper: CalculatingResultMapper
) : ViewModel() {

    val viewState: LiveData<CalculatorViewState> get() = _viewState
    private val _viewState = MutableLiveData<CalculatorViewState>()
    private val session get() = requireNotNull(getSesionUseCase.obtener())

    fun fetchCampaign() {
        viewModelScope.launch(handler) {
            io {
                val request = calculatorResultUseCase.list()
                val campaign = getCampaignUseCase.obtenerCampaniaActual().nombreCorto
                val isChristmas = isChristmas(campaign)
                val attributesModel =
                    CalculatorAttributesModel(
                        isChristmas,
                        isDiamond()
                    )
                _viewState.postValue(
                    CalculatorViewState.Success(attributesModel, calculatingResultMapper.transformResultado(request.firstOrNull()))
                )
            }
        }
    }

    private fun isChristmas(campaign: String): Boolean =
        Constant.CAMPANIAS_NAVIDAD.any { it == campaign }

    private fun isDiamond(): Boolean = session.isDiamond

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value =
                CalculatorViewState.Failed(
                    message
                )
        }
    }
}
