package biz.belcorp.salesforce.modules.calculator.feature.calculator.base

import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel

sealed class CalculatorViewState {
    class Success(val attributesModel: CalculatorAttributesModel, val result: CalculatingResultModel?) : CalculatorViewState()
    class Failed(val message: String) : CalculatorViewState()
}
