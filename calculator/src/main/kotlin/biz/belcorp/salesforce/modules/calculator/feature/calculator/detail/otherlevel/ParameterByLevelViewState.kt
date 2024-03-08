package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel

import biz.belcorp.salesforce.modules.calculator.model.LevelParameterModel

sealed class ParameterByLevelViewState {
    class Success(val data: LevelParameterModel?) :
        ParameterByLevelViewState()
    class Failed(val message: String) : ParameterByLevelViewState()
}
