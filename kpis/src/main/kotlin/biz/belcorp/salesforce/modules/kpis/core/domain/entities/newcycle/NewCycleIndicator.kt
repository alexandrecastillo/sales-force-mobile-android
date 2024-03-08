package biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

data class NewCycleIndicator(
    var campaign: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val profile: String = EMPTY_STRING,
    val highValueOrders6d6: Int = NUMBER_ZERO,
    val highValueOrders5d5: Int = NUMBER_ZERO,
    val highValueOrders4d4: Int = NUMBER_ZERO,
    val highValueOrders3d3: Int = NUMBER_ZERO,
    val highValueOrders2d2: Int = NUMBER_ZERO,
    val highValueOrders1d1: Int = NUMBER_ZERO,
    val highValueOrdersRetentionPercentage: Double = ZERO_DECIMAL,
    val lowValueOrders6d6: Int = NUMBER_ZERO,
    val lowValueOrders5d5: Int = NUMBER_ZERO,
    val lowValueOrders4d4: Int = NUMBER_ZERO,
    val lowValueOrders3d3: Int = NUMBER_ZERO,
    val lowValueOrders2d2: Int = NUMBER_ZERO,
    val lowValueOrders1d1: Int = NUMBER_ZERO,
    val lowValueOrdersRetentionPercentage: Double = ZERO_DECIMAL
)
