package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model

import biz.belcorp.salesforce.core.constants.Constant

data class OrderModel(
    val title: String? = Constant.EMPTY_STRING,
    var unitsExpected: Int? = Constant.NUMBER_ZERO,
    var unitsReal: Int? = Constant.NUMBER_ZERO,
    var gainPerOrder: Float? = Constant.ZERO_FLOAT,
    var gainPerOrderNotSuccess: Float? = Constant.ZERO_FLOAT,
    var gainPerOrderExpected: Float? = Constant.ZERO_FLOAT,
)
