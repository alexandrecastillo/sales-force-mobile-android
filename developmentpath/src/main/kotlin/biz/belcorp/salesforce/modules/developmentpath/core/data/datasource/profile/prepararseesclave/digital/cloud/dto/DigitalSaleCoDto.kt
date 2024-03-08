package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalSaleCoDto(
    @SerialName(COLLECTION_CO_KEY)
    val digitalSaleList: List<DigitalSale> = emptyList()
) {
    @Serializable
    data class DigitalSale(
        @SerialName(CAMPAIGN_KEY)
        val campaign: String,
        @SerialName(REGION_KEY)
        val region: String? = Constant.EMPTY_STRING,
        @SerialName(ZONE_KEY)
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName(SECTION_KEY)
        val section: String? = Constant.EMPTY_STRING,
        @SerialName(CONSULTANT_CODE_KEY)
        val consultantCode: String,
        @SerialName(GANAMAS_KEY)
        val ganamas: GanaMas,
        @SerialName(USABILITY_KEY)
        val usability: Usability,
        @SerialName(ONLINE_STORE_KEY)
        val onlineStore: OnlineStore,
        @SerialName(DIGITAL_CATALOG_KEY)
        val digitalCatalog: DigitalCatalog,
        @SerialName(BUY_DIGITAL_OFFERS_KEY)
        val buyDigitalOffers: Boolean? = false,
        @SerialName(OPEN_VIRTUAL_COACH_KEY)
        val openVirtualCoach: Boolean? = false,
        @SerialName(ORDER_SENT_APP_KEY)
        val orderSentApp: Boolean? = false,
        @SerialName(UNIQUE_IP_KEY)
        val uniqueIp: Boolean? = false
    ) {
        @Serializable
        data class GanaMas(
            @SerialName(IS_SUBSCRIBED_KEY)
            val isSubscribed: Boolean? = false,
            @SerialName(CAMPAIGN_SUBSCRIPTION_KEY)
            val campaignSubscription: String = Constant.EMPTY_STRING,
            @SerialName(BUY_KEY)
            val buy: Boolean? = false
        )

        @Serializable
        data class Usability(
            @SerialName(DIGITAL_CATALOG_KEY)
            val digitalCatalog: Boolean? = false,
            @SerialName(MAKEUP_APP_KEY)
            val makeupApp: Boolean? = false
        )

        @Serializable
        data class OnlineStore(
            @SerialName(BUY_KEY)
            val buy: Boolean? = false,
            @SerialName(SHARE_KEY)
            val share: Boolean? = false,
            @SerialName(IS_SUBSCRIBED_KEY)
            val isSubscribed: Boolean? = false,
            @SerialName(CAMPAIGN_SUBSCRIPTION_KEY)
            val campaignSubscription: String,
            @SerialName(MTO_SALES_KEY)
            val mtoSales: Double = ZERO_DECIMAL
        )

        @Serializable
        data class DigitalCatalog(
            @SerialName(SHARED_CATALOGS_KEY)
            val sharedCatalogs: Int = Constant.NUMBER_ZERO,
            @SerialName(RECEIVE_ORDERS_KEY)
            val receiveOrders: Boolean? = false,
            @SerialName(APPROVE_ORDERS_KEY)
            val approveOrders: Boolean? = false,
            @SerialName(APPROVE_PRE_ORDERS_KEY)
            val approvePreOrders: Int = Constant.NUMBER_ZERO,
            @SerialName(RECEIVE_PRE_ORDERS_KEY)
            val receivePreOrders: Int = Constant.NUMBER_ZERO,
            @SerialName(DIGITAL_CATALOG_QUANTITY_SHARE_KEY)
            val quantitySharedCatalogs: Int = Constant.NUMBER_ZERO,
        )
    }
}
