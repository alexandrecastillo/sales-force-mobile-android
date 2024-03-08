package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital

import biz.belcorp.salesforce.core.constants.Constant

class DigitalSaleBusinessPartner(
    campaign: String = Constant.EMPTY_STRING,
    region: String = Constant.EMPTY_STRING,
    zone: String = Constant.EMPTY_STRING,
    section: String = Constant.EMPTY_STRING,
    var ganamasSubscribed: Int = Constant.NUMBER_ZERO,
    var ganamasSubscribedBuyers: Int = Constant.NUMBER_ZERO,
    var ganamasSubscribedNotBuyers: Int = Constant.NUMBER_ZERO,
    var ganamasNotSubscribedBuyers: Int = Constant.NUMBER_ZERO,
    var ganamasNotSubscribedNotBuyers: Int = Constant.NUMBER_ZERO,
    var digitalCatalogShareCatalog: Int = Constant.NUMBER_ZERO,
    var digitalCatalogReceiveOrders: Int = Constant.NUMBER_ZERO,
    var digitalCatalogApproveOrders: Int = Constant.NUMBER_ZERO,
    var virtualCoachOpen: Int = Constant.NUMBER_ZERO,
    var virtualCoachReceive: Int = Constant.NUMBER_ZERO,
    var virtualCoachClick: Int = Constant.NUMBER_ZERO,
    var makeupAppUsability: Int = Constant.NUMBER_ZERO,
    var virtualStoreSubscribed: Int = Constant.NUMBER_ZERO,
    var esikaAhoraSubscribed: Int = Constant.NUMBER_ZERO,
    var uniqueIpPercentage: Double = Constant.EMPTY_DOUBLE,
    var finalActiveConsultants: Int = Constant.NUMBER_ZERO,
    var orderSentApp: Int = Constant.NUMBER_ZERO,
    var multiBrand: Int = Constant.NUMBER_ZERO
) : DigitalSale(campaign, region, zone, section)
