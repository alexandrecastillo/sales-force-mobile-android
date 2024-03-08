package biz.belcorp.salesforce.modules.consultants.features.list.models

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.consultants.R

enum class Indicator(
    var indicatorName: String = Constant.EMPTY_STRING,
    val indicatorColor: Int,
    val indicatorAccentColor: Int,
    val indicatorId: Int
) {

    CambioNivel(
        indicatorColor = R.color.indicator_level,
        indicatorAccentColor = R.color.indicator_level_light,
        indicatorId = -1
    )

}
