package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel
import biz.belcorp.salesforce.modules.calculator.model.PartnerVariableModel

sealed class CalculatorResultViewState {
    class Success(val partnerVariable: PartnerVariableModel) : CalculatorResultViewState()
    class Inserted(val calculatorResultModel: CalculatingResultModel) : CalculatorResultViewState()
    object Deleted : CalculatorResultViewState()
    class Failed(val message: String) : CalculatorResultViewState()
}
