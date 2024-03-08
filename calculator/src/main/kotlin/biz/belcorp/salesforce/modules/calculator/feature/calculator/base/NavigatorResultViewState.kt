package biz.belcorp.salesforce.modules.calculator.feature.calculator.base

import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel

sealed class NavigatorResultViewState {
    class RESULT(val params: CalculatingResultModel) : NavigatorResultViewState()
    object RESET : NavigatorResultViewState()
    class CLEAR(val params: CalculatingResultModel) : NavigatorResultViewState()
}
