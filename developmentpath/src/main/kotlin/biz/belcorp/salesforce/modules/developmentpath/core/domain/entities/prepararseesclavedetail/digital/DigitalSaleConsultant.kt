package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital

import biz.belcorp.salesforce.core.constants.Constant

class DigitalSaleConsultant(
    campaign: String = Constant.EMPTY_STRING,
    region: String = Constant.EMPTY_STRING,
    zone: String = Constant.EMPTY_STRING,
    section: String = Constant.EMPTY_STRING,
    var consultantCode: String = Constant.EMPTY_STRING,
    var ganamasIsSubscribed: Boolean = false,
    var ganamasCampaignSubscription: String = Constant.EMPTY_STRING,
    var ganamasBuy: Boolean = false,
    var usabilityDigitalCatalog: Boolean = false,
    var usabilityMakeupApp: Boolean = false,
    var onlineStoreBuy: Boolean = false,
    var onlineStoreShare: Boolean = false,
    var onlineStoreIsSuscribed: Boolean = false,
    var onlineCampaignSubscription: String = Constant.EMPTY_STRING,
    var onlineStoreMtoSales: Double = 0.0,
    var digitalCatalogSharedCatalogs: Int = Constant.NUMBER_ZERO,
    var digitalCatalogReceiveOrders: Boolean = false,
    var digitalCatalogReceivePreOrders: Int = Constant.NUMBER_ZERO,
    var digitalCatalogApprovePreOrders: Int = Constant.NUMBER_ZERO,
    var digitalCatalogQuantitySharedCatalogs: Int = Constant.NUMBER_ZERO,
    var digitalCatalogApproveOrders: Boolean = false,
    var buyDigitalOffers: Boolean = false,
    var openVirtualCoach: Boolean = false,
    var orderSentApp: Boolean = false,
    var uniqueIp: Boolean = false,
) : DigitalSale(campaign, region, zone, section)
