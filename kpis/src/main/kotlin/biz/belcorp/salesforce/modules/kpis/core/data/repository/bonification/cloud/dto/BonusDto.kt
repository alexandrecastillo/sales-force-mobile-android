package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BonusDto(
    @SerialName(BONUS_COLLECTION_KEY)
    val list: List<Bonus> = emptyList()
) {

    @Serializable
    data class Bonus(
        @SerialName(BONUS_CAMPAIGN_KEY)
        val campaign: String,
        @SerialName(BONUS_REGION_KEY)
        val region: String,
        @SerialName(BONUS_ZONE_KEY)
        val zone: String,
        @SerialName(BONUS_SECTION_KEY)
        val section: String,
        @SerialName(BONUS_CODE_KEY)
        val code: String,
        @SerialName(BONUS_UNIT_AMOUNT_KEY)
        val amount: Double
    )

}
