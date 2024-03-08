package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProfitDto(
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
        val region: String? = EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = EMPTY_STRING,
        @SerialName(KEY_PROF_TOTAL)
        val total: Double = ZERO_DECIMAL,
        @SerialName(KEY_COMPETITION)
        val competition: Competition,
        @SerialName(KEY_PROF_ORDERS)
        val orders: Order
    ) {
        @Serializable
        data class Competition(
            @SerialName(KEY_PROF_TOTAL)
            val total: Double = ZERO_DECIMAL,
            @SerialName(KEY_CAPITALIZATION)
            val capitalization: Double = ZERO_DECIMAL,
            @SerialName(KEY_6D6)
            val _6d6: _6d6,
            @SerialName(KEY_CHANGE_LEVEL)
            val changeLevel: Double = ZERO_DECIMAL,
            @SerialName(KEY_NEW_FIXED)
            val newFixed: Double = ZERO_DECIMAL,
            @SerialName(KEY_PRODUCTS_RELEASE)
            val productsRelease: Double = ZERO_DECIMAL,
            @SerialName(KEY_COMPETITION_TACTIC_BONUS)
            val tacticBonus: TacticBonus
        )

            @Serializable
            data class _6d6(
                @SerialName(KEY_6D6_LOW_VALUE)
                val lowValue : Double = ZERO_DECIMAL,
                @SerialName(KEY_6D6_HIGH_VALUE)
                val highValue : Double = ZERO_DECIMAL,
                @SerialName(KEY_6D6_TOTAL)
                val total : Double = ZERO_DECIMAL
            )

            @Serializable
            data class TacticBonus(
                @SerialName(KEY_COMPETITION_TACTIC_BONUS_LEVEL)
                val level: String? = EMPTY_STRING,
                @SerialName(KEY_COMPETITION_TACTIC_BONUS_AMOUNT)
                val amount : Double = ZERO_DECIMAL,
            )


        @Serializable
        data class Order(
            @SerialName(KEY_PROF_TOTAL)
            val total: Double = ZERO_DECIMAL,
            @SerialName(KEY_POTENTIAL)
            val potential: Double = ZERO_DECIMAL,
            @SerialName(KEY_PROF_RANGES)
            val ranges: List<Range> = emptyList()
        )

        @Serializable
        data class Range(
            @SerialName(KEY_PROF_POS)
            val pos: Int = NUMBER_ZERO,
            @SerialName(KEY_PROF_RANGE)
            val range: String = EMPTY_STRING,
            @SerialName(KEY_AMOUNT)
            val amount: Double = ZERO_DECIMAL
        )
    }
}
