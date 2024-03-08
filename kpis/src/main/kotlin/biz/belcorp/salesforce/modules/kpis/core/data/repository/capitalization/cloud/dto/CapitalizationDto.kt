package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CapitalizationDto(
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
        val capitalization: Capitalization,
        @SerialName(KEY_PEGS)
        val pegs: Peg,
        @SerialName(KEY_POTENTIAL)
        val potential: Potential
    ) {
        @Serializable
        data class Capitalization(
            @SerialName(KEY_REAL)
            val real: Int = NUMBER_ZERO,
            @SerialName(KEY_GOAL)
            val goal: Int = NUMBER_ZERO,
            @SerialName(KEY_CAPITALIZATION_FULFILLMENT)
            val capitalizationFulfillment: Double = ZERO_DECIMAL,
            @SerialName(KEY_PROJECTED)
            val projected: Int = NUMBER_ZERO,
            @SerialName(KEY_ENTRIES)
            val entries: Int = NUMBER_ZERO,
            @SerialName(KEY_ENTRIES_GOAL)
            val entriesGoal: Int = NUMBER_ZERO,
            @SerialName(KEY_REENTRIES)
            val reentries: Int = NUMBER_ZERO,
            @SerialName(KEY_EXPENSES)
            val expenses: Int = NUMBER_ZERO,
            @SerialName(KEY_PROACTIVE)
            val proactive: Int = NUMBER_ZERO,
            @SerialName(KEY_SUCCESS)
            val success: Int = NUMBER_ZERO
        )

        @Serializable
        data class Peg(
            @SerialName(KEY_REAL)
            val real: Int = NUMBER_ZERO,
            @SerialName(KEY_RETENTION)
            val retention: Retention
        ) {
            @Serializable
            data class Retention(
                @SerialName(KEY_GOAL)
                val goal: Int = NUMBER_ZERO,
                @SerialName(KEY_REAL)
                val real: Int = NUMBER_ZERO,
                @SerialName(KEY_PERCENTAGE)
                val percentage: Double = ZERO_DECIMAL,
                @SerialName(KEY_REMAINING)
                val remaining: Int = NUMBER_ZERO
            )
        }

        @Serializable
        data class Potential(
            @SerialName(KEY_TOTAL)
            val total: Int = NUMBER_ZERO,
            @SerialName(KEY_ENTRIES)
            val entries: Int = NUMBER_ZERO,
            @SerialName(KEY_REENTRIES)
            val reentries: Int = NUMBER_ZERO
        )
    }
}
