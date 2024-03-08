package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProfileProfitDto(
    @SerialName(COLLECTION_PROFIT_KEY)
    val kpiGain: List<KpiGain> = emptyList()
) {
    @Serializable
    data class KpiGain(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_PROFILE)
        val profile: String,
        @SerialName(KEY_REGION)
        val region: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_PROF_TOTAL)
        val total: Double = Constant.ZERO_DECIMAL
    )

}
