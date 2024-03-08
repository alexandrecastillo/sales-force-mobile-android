package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant

class ActivesRetention(
    var campaign: String = Constant.EMPTY_STRING,
    var activesReal: Int = Constant.NUMBER_ZERO,
    var activesLastYear: Int = Constant.NUMBER_ZERO
)
