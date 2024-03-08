package biz.belcorp.salesforce.core.entities.digitalsale

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DigitalSaleSeEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var ganamasSubscribed: Int = NUMBER_ZERO,
    var ganamasSubscribedBuyers: Int = NUMBER_ZERO,
    var ganamasSubscribedNotBuyers: Int = NUMBER_ZERO,
    var ganamasNotSubscribedBuyers: Int = NUMBER_ZERO,
    var ganamasNotSubscribedNotBuyers: Int = NUMBER_ZERO,
    var digitalCatalogShareCatalog: Int = NUMBER_ZERO,
    var digitalCatalogReceiveOrders: Int = NUMBER_ZERO,
    var digitalCatalogApproveOrders: Int = NUMBER_ZERO,
    var virtualCoachOpen: Int = NUMBER_ZERO,
    var virtualCoachReceive: Int = NUMBER_ZERO,
    var virtualCoachClick: Int = NUMBER_ZERO,
    var makeupAppUsability: Int = NUMBER_ZERO,
    var esikaAhoraSubscribed: Int = NUMBER_ZERO,
    var uniqueIpPercentage: Double = EMPTY_DOUBLE,
    var finalActiveConsultants: Int = NUMBER_ZERO,
    var orderSentApp: Int = NUMBER_ZERO,
    var multiBrand: Int = NUMBER_ZERO,
    @Id var id: Long = NUMBER_ZERO.toLong()
)
