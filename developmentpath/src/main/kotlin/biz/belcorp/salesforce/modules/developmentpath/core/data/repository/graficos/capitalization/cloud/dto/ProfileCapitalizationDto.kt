package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProfileCapitalizationDto(
    @SerialName(COLLECTION_CAPITALIZATION_KEY)
    val kpiCapitalization: List<KpiCapitalization> = emptyList()
) {
    @Serializable
    data class KpiCapitalization(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_PROFILE)
        val profile: String,
        @SerialName(KEY_REGION)
        val region: String? = EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = EMPTY_STRING,
        @SerialName(KEY_CAPITALIZATION)
        val capitalization: Capitalization
    ) {
        @Serializable
        data class Capitalization(
            @SerialName(KEY_REAL)
            val real: Int = NUMBER_ZERO,
            @SerialName(KEY_ENTRIES)
            val entries: Int = NUMBER_ZERO,
            @SerialName(KEY_REENTRIES)
            val reentries: Int = NUMBER_ZERO,
            @SerialName(KEY_EXPENSES)
            val expenses: Int = NUMBER_ZERO
        )
    }

}
