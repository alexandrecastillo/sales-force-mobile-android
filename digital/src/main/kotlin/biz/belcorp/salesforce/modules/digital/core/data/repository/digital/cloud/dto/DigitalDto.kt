package biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DigitalDto(
    @SerialName(DIGITAL_COLLECTION_KEY)
    val info: List<Digital>
) {

    @Serializable
    class Digital(
        @SerialName(DIGITAL_CAMPAIGN_KEY)
        val campaign: String,
        @SerialName(DIGITAL_REGION_KEY)
        val region: String,
        @SerialName(DIGITAL_ZONE_KEY)
        val zone: String,
        @SerialName(DIGITAL_SECTION_KEY)
        val section: String,
        @SerialName(DIGITAL_PROFILE_KEY)
        val profile: String,
        @SerialName(DIGITAL_ACTIVES_KEY)
        val actives: Int,
        @SerialName(DIGITAL_SUBSCRIBED_KEY)
        val subscribed: Int,
        @SerialName(DIGITAL_SHARE_KEY)
        val share: Int,
        @SerialName(DIGITAL_BUY_KEY)
        val buy: Int,
        @SerialName(DIGITAL_SUBSCRIBED_ACTIVES_RATIO_KEY)
        val subscribedActivesRatio: Float,
        @SerialName(DIGITAL_SHARE_ACTIVES_RATIO_KEY)
        val shareActivesRatio: Float,
        @SerialName(DIGITAL_SHARE_SUBSCRIBED_RATIO_KEY)
        val shareSubscribedRatio: Float,
        @SerialName(DIGITAL_BUY_ACTIVES_RATIO_KEY)
        val buyActivesRatio: Float,
        @SerialName(DIGITAL_BUY_SUBSCRIBED_RATIO_KEY)
        val buySubscribedRatio: Float
    )

}
