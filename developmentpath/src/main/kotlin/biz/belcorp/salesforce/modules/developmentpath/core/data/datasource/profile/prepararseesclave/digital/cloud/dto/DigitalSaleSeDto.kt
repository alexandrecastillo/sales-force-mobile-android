package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.COLLECTION_SE_KEY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalSaleSeDto(
    @SerialName(COLLECTION_SE_KEY)
    val digitalSaleList: List<DigitalSale> = emptyList()
) {
    @Serializable
    data class DigitalSale(
        @SerialName(CAMPAIGN_KEY)
        val campaign: String,
        @SerialName(REGION_KEY)
        val region: String? = EMPTY_STRING,
        @SerialName(ZONE_KEY)
        val zone: String? = EMPTY_STRING,
        @SerialName(SECTION_KEY)
        val section: String? = EMPTY_STRING,
        @SerialName(GANAMAS_KEY)
        val ganamas: GanaMas,
        @SerialName(DIGITAL_CATALOG_KEY)
        val digitalCatalog: DigitalCatalog,
        @SerialName(VIRTUAL_COACH_KEY)
        val virtualCoach: VirtualCoach,
        @SerialName(MAKEUP_APP_KEY)
        val makeupApp: MakeupApp,
        @SerialName(ESIKA_AHORA_KEY)
        val esikaAhora: EsikaAhora,
        @SerialName(UNIQUE_IP_PERCENTAGE_KEY)
        val uniqueIpPercentage: Double? = EMPTY_DOUBLE,
        @SerialName(FINAL_ACTIVE_CONSULTANTS_KEY)
        val finalActiveConsultants: Int? = NUMBER_ZERO,
        @SerialName(ORDER_SENT_APP_KEY)
        val orderSentApp: Int? = NUMBER_ZERO,
        @SerialName(MULTI_BRAND_KEY)
        val multiBrand: Int? = NUMBER_ZERO
    ) {
        @Serializable
        data class GanaMas(
            @SerialName(SUBSCRIBED_KEY)
            val subscribed: Int? = NUMBER_ZERO,
            @SerialName(SUBSCRIBED_BUYERS_KEY)
            val subscribedBuyers: Int? = NUMBER_ZERO,
            @SerialName(SUBSCRIBED_NOT_BUYERS_KEY)
            val subscribedNotBuyers: Int? = NUMBER_ZERO,
            @SerialName(NOT_SUBSCRIBED_BUYERS_KEY)
            val notSubscribedBuyers: Int? = NUMBER_ZERO,
            @SerialName(NOT_SUBSCRIBED_NOT_BUYERS_KEY)
            val notSubscribedNotBuyers: Int? = NUMBER_ZERO
        )

        @Serializable
        data class VirtualCoach(
            @SerialName(OPEN_KEY)
            val open: Int? = NUMBER_ZERO,
            @SerialName(RECEIVE_KEY)
            val receive: Int? = NUMBER_ZERO,
            @SerialName(CLICK_KEY)
            val click: Int? = NUMBER_ZERO
        )

        @Serializable
        data class MakeupApp(
            @SerialName(USABILITY_KEY)
            val usability: Int? = NUMBER_ZERO
        )

        @Serializable
        data class EsikaAhora(
            @SerialName(SUBSCRIBED_KEY)
            val subscribed: Int? = NUMBER_ZERO
        )


        @Serializable
        data class DigitalCatalog(
            @SerialName(SHARE_CATALOG_KEY)
            val shareCatalog: Int = NUMBER_ZERO,
            @SerialName(RECEIVE_ORDERS_KEY)
            val receiveOrders: Int = NUMBER_ZERO,
            @SerialName(APPROVE_ORDERS_KEY)
            val approveOrders: Int = NUMBER_ZERO
        )
    }
}

