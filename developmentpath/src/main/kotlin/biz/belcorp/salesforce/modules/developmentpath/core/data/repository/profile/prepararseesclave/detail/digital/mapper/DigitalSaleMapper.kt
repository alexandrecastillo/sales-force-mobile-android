package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleEntity
import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleSeEntity
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleSeDto
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleConsultant

class DigitalSaleMapper {

    fun map(digitalSale: DigitalSaleCoDto): List<DigitalSaleEntity> {
        return digitalSale.digitalSaleList.map { map(it) }
    }

    fun map(digitalSale: DigitalSaleSeDto): List<DigitalSaleSeEntity> {
        return digitalSale.digitalSaleList.map { map(it) }
    }

    private fun map(digitalSale: DigitalSaleSeDto.DigitalSale): DigitalSaleSeEntity {
        with(digitalSale) {
            return DigitalSaleSeEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                ganamasSubscribed = ganamas.subscribed.zeroIfNull(),
                ganamasSubscribedBuyers = ganamas.subscribedBuyers.zeroIfNull(),
                ganamasSubscribedNotBuyers = ganamas.subscribedNotBuyers.zeroIfNull(),
                ganamasNotSubscribedBuyers = ganamas.notSubscribedBuyers.zeroIfNull(),
                ganamasNotSubscribedNotBuyers = ganamas.notSubscribedNotBuyers.zeroIfNull(),
                digitalCatalogShareCatalog = digitalCatalog.shareCatalog.zeroIfNull(),
                digitalCatalogReceiveOrders = digitalCatalog.receiveOrders.zeroIfNull(),
                digitalCatalogApproveOrders = digitalCatalog.approveOrders.zeroIfNull(),
                virtualCoachOpen = virtualCoach.open.zeroIfNull(),
                virtualCoachReceive = virtualCoach.receive.zeroIfNull(),
                virtualCoachClick = virtualCoach.click.zeroIfNull(),
                makeupAppUsability = makeupApp.usability.zeroIfNull(),
                esikaAhoraSubscribed = esikaAhora.subscribed.zeroIfNull(),
                uniqueIpPercentage = uniqueIpPercentage.zeroIfNull(),
                finalActiveConsultants = finalActiveConsultants.zeroIfNull(),
                orderSentApp = orderSentApp.zeroIfNull(),
                multiBrand = multiBrand.zeroIfNull()
            )
        }
    }

    private fun map(digitalSale: DigitalSaleCoDto.DigitalSale): DigitalSaleEntity =
        with(digitalSale) {
            return DigitalSaleEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                consultantCode = consultantCode,
                ganamasIsSubscribed = ganamas.isSubscribed ?: false,
                ganamasCampaignSubscription = ganamas.campaignSubscription,
                ganamasBuy = ganamas.buy ?: false,
                usabilityDigitalCatalog = usability.digitalCatalog ?: false,
                usabilityMakeupApp = usability.makeupApp ?: false,
                onlineStoreBuy = onlineStore.buy ?: false,
                onlineStoreShare = onlineStore.share ?: false,
                onlineStoreIsSuscribed = onlineStore.isSubscribed ?: false,
                onlineStoreCampaignSubscription = onlineStore.campaignSubscription,
                onlineStoreMtoSales = onlineStore.mtoSales,
                digitalCatalogApproveOrders = digitalCatalog.approveOrders ?: false,
                digitalCatalogReceiveOrders = digitalCatalog.receiveOrders ?: false,
                digitalCatalogSharedCatalogs = digitalCatalog.sharedCatalogs,
                digitalCatalogApprovedPreOrders = digitalCatalog.approvePreOrders,
                digitalCatalogReceivedPreOrders = digitalCatalog.receivePreOrders,
                digitalCatalogQuantitySharedCatalogs = digitalCatalog.quantitySharedCatalogs,
                buyDigitalOffers = buyDigitalOffers ?: false,
                openVirtualCoach = openVirtualCoach ?: false,
                orderSentApp = orderSentApp ?: false,
                uniqueIp = uniqueIp ?: false
            )
        }

    fun map(entity: DigitalSaleEntity): DigitalSaleConsultant = with(entity) {
        return DigitalSaleConsultant(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            consultantCode = consultantCode,
            ganamasIsSubscribed = ganamasIsSubscribed,
            ganamasCampaignSubscription = ganamasCampaignSubscription,
            ganamasBuy = ganamasBuy,
            usabilityDigitalCatalog = usabilityDigitalCatalog,
            usabilityMakeupApp = usabilityMakeupApp,
            onlineStoreBuy = onlineStoreBuy,
            onlineStoreShare = onlineStoreShare,
            onlineStoreIsSuscribed = onlineStoreIsSuscribed,
            onlineCampaignSubscription = onlineStoreCampaignSubscription,
            onlineStoreMtoSales = onlineStoreMtoSales,
            digitalCatalogApproveOrders = digitalCatalogApproveOrders,
            digitalCatalogReceiveOrders = digitalCatalogReceiveOrders,
            digitalCatalogSharedCatalogs = digitalCatalogSharedCatalogs,
            digitalCatalogApprovePreOrders = digitalCatalogApprovedPreOrders,
            digitalCatalogReceivePreOrders = digitalCatalogReceivedPreOrders,
            digitalCatalogQuantitySharedCatalogs = digitalCatalogQuantitySharedCatalogs,
            buyDigitalOffers = buyDigitalOffers,
            openVirtualCoach = openVirtualCoach,
            orderSentApp = orderSentApp,
            uniqueIp = uniqueIp
        )
    }

    fun map(entity: DigitalSaleSeEntity): DigitalSaleBusinessPartner = with(entity) {
        return DigitalSaleBusinessPartner(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            ganamasSubscribed = ganamasSubscribed.zeroIfNull(),
            ganamasSubscribedBuyers = ganamasSubscribedBuyers.zeroIfNull(),
            ganamasSubscribedNotBuyers = ganamasSubscribedNotBuyers.zeroIfNull(),
            ganamasNotSubscribedBuyers = ganamasNotSubscribedBuyers.zeroIfNull(),
            ganamasNotSubscribedNotBuyers = ganamasNotSubscribedNotBuyers.zeroIfNull(),
            digitalCatalogShareCatalog = digitalCatalogShareCatalog.zeroIfNull(),
            digitalCatalogReceiveOrders = digitalCatalogReceiveOrders.zeroIfNull(),
            digitalCatalogApproveOrders = digitalCatalogApproveOrders.zeroIfNull(),
            virtualCoachOpen = virtualCoachOpen.zeroIfNull(),
            virtualCoachReceive = virtualCoachReceive.zeroIfNull(),
            virtualCoachClick = virtualCoachClick.zeroIfNull(),
            makeupAppUsability = makeupAppUsability.zeroIfNull(),
            esikaAhoraSubscribed = esikaAhoraSubscribed.zeroIfNull(),
            uniqueIpPercentage = uniqueIpPercentage.zeroIfNull(),
            finalActiveConsultants = finalActiveConsultants.zeroIfNull(),
            orderSentApp = orderSentApp.zeroIfNull(),
            multiBrand = multiBrand.zeroIfNull()
        )
    }

}
