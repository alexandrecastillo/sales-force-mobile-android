package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model

import biz.belcorp.salesforce.core.constants.Constant

class ChartEntryModel(
    val caption: String,
    val maxValue: Float = Constant.NUMBER_ONE_HUNDRED.toFloat(),
    val firstValue: Float,
    val secondValue: Float = Constant.ZERO_FLOAT
)
