package biz.belcorp.salesforce.core.entities.newcycles

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class NewCycleEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var profile: String = EMPTY_STRING,
    var highValueOrders6d6: Int = NUMBER_ZERO,
    var highValueOrders5d5: Int = NUMBER_ZERO,
    var highValueOrders4d4: Int = NUMBER_ZERO,
    var highValueOrders3d3: Int = NUMBER_ZERO,
    var highValueOrders2d2: Int = NUMBER_ZERO,
    var highValueOrders1d1: Int = NUMBER_ZERO,
    var highValueOrdersRetentionPercentage: Double = ZERO_DECIMAL,
    var lowValueOrders6d6: Int = NUMBER_ZERO,
    var lowValueOrders5d5: Int = NUMBER_ZERO,
    var lowValueOrders4d4: Int = NUMBER_ZERO,
    var lowValueOrders3d3: Int = NUMBER_ZERO,
    var lowValueOrders2d2: Int = NUMBER_ZERO,
    var lowValueOrders1d1: Int = NUMBER_ZERO,
    var lowValueOrdersRetentionPercentage: Double = ZERO_DECIMAL,
    @Id var id: Long = NUMBER_ZERO.toLong()
)
