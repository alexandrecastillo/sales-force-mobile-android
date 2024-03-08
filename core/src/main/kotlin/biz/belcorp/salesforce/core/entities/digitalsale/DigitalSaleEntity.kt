package biz.belcorp.salesforce.core.entities.digitalsale

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.db.objectbox.converters.NullToEmptyStringConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DigitalSaleEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var consultantCode: String = EMPTY_STRING,
    var ganamasIsSubscribed: Boolean = false,
    var ganamasCampaignSubscription: String = EMPTY_STRING,
    var ganamasBuy: Boolean = false,
    var usabilityDigitalCatalog: Boolean = false,
    var usabilityMakeupApp: Boolean = false,
    var onlineStoreBuy: Boolean = false,
    var onlineStoreShare: Boolean = false,
    var onlineStoreIsSuscribed: Boolean = false,
    var onlineStoreMtoSales: Double = 0.0,
    @Convert(dbType = String::class, converter = NullToEmptyStringConverter::class)
    var onlineStoreCampaignSubscription: String = EMPTY_STRING,
    var esikaAhoraBuy: Boolean = false,
    var esikaAhoraSuscribedTo: Boolean = false,
    var digitalCatalogSharedCatalogs: Int = NUMBER_ZERO,
    var digitalCatalogReceiveOrders: Boolean = false,
    var digitalCatalogApproveOrders: Boolean = false,
    var buyDigitalOffers: Boolean = false,
    var openVirtualCoach: Boolean = false,
    var orderSentApp: Boolean = false,
    var uniqueIp: Boolean = false,
    @Id var id: Long = NUMBER_ZERO.toLong(),
    var digitalCatalogReceivedPreOrders: Int = 0,
    var digitalCatalogApprovedPreOrders: Int = 0,
    var digitalCatalogQuantitySharedCatalogs: Int = 0,
)

