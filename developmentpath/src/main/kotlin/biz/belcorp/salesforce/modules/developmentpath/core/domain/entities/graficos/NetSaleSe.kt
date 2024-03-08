package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

class NetSaleSe(
    val campaign: String = EMPTY_STRING,
    val amount: Double = ZERO_DECIMAL,
    val average: Double = ZERO_DECIMAL
)
