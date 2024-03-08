package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant

class CapitalizationSe(
    var campaign: String = Constant.EMPTY_STRING,
    var entries: Int = Constant.NUMBER_ZERO,
    var reentries: Int = Constant.NUMBER_ZERO,
    var expenses: Int = Constant.NUMBER_ZERO,
    var real: Int = Constant.NUMBER_ZERO
)
