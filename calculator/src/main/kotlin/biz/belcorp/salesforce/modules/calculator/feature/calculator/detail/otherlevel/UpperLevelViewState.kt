package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel

import biz.belcorp.salesforce.modules.calculator.model.UpperLevelModel

sealed class UpperLevelViewState {
    class Success(val data: List<UpperLevelModel>?) :
        UpperLevelViewState()
    class Failed(val message: String) : UpperLevelViewState()
}
