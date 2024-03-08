package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalDto(
    @SerialName(DIGITAL_COLLECTION_KEY)
    val digitalInfo: List<OnlineStore>
) {

    @Serializable
    data class OnlineStore(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_CONSULTANT_CODE)
        val consultantCode: String,
        @SerialName(KEY_IS_SUBSCRIBED)
        val isSubscribed: Boolean,
        @SerialName(KEY_SHARE)
        val share: Boolean,
        @SerialName(KEY_BUY)
        val buy: Boolean,
        @SerialName(KEY_SALE)
        val sale: Float,
        @SerialName(KEY_ORDERS)
        val orders: Int
    )

}

