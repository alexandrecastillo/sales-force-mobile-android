package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.constants.Constant

data class RetentionIndicator(
    var campaign: String = Constant.EMPTY_STRING,
    var profile: String = Constant.EMPTY_STRING,
    var region: String = Constant.EMPTY_STRING,
    var zone: String = Constant.EMPTY_STRING,
    var section: String = Constant.EMPTY_STRING,
    val _3d3High: Double?  = Constant.ZERO_DECIMAL,
    val _4d4High: Double?  = Constant.ZERO_DECIMAL,
    val _5d5High: Double?  = Constant.ZERO_DECIMAL,
    val _6d6High: Double?  = Constant.ZERO_DECIMAL,
    val retention_percentageHigh: Double?  = Constant.ZERO_DECIMAL,
    val _1d1Low: Double?  = Constant.ZERO_DECIMAL,
    val _2d2Low: Double?  = Constant.ZERO_DECIMAL,
    val _3d3Low: Double?  = Constant.ZERO_DECIMAL,
    val _4d4Low: Double?  = Constant.ZERO_DECIMAL,
    val _5d5Low: Double?  = Constant.ZERO_DECIMAL,
    val _6d6Low: Double?  = Constant.ZERO_DECIMAL,
    val retention_percentageLow: Double?  = Constant.ZERO_DECIMAL,
)
