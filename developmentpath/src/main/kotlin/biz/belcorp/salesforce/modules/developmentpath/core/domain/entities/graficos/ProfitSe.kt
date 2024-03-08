package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant

class ProfitSe(
    var campaign: String = Constant.EMPTY_STRING,
    var total: Float = Constant.ZERO_FLOAT
)
