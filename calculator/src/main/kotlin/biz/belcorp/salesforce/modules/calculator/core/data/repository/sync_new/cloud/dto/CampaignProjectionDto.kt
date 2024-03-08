package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignProjectionDto(
    @SerialName(COLLECTION_KEY)
    val campaignProjection: CampaignProjection
) {
    @Serializable
    data class CampaignProjection(
        @SerialName(KEY_REGION)
        val region: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_CAMPAIGN)
        val campaign: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_CREATED)
        val dateCreated: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_EDITED)
        val dateEdited: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_ACTIVITY_PROJECTED_PERCENTAGE)
        val activityProjectedPercentage: Int? = Constant.NUMBER_ZERO,
        @SerialName(KEY_PEGS_PROJECTED_NEXT_CAMPAIGN)
        val pegsProjectedNextCampaign: Int? = Constant.NUMBER_ZERO,
        @SerialName(KEY_CAPITALIZATION)
        val capitalization: Capitalization,
        @SerialName(KEY_ORDERS)
        val orders: Orders,
        @SerialName(KEY_RETENTION_6D6)
        val retention6D6: Retention6d6,
        @SerialName(KEY_PROJECT_PROFIT_SUMMARY)
        val projectProfitSummary: ProjectProfitSummary
    ) {

        @Serializable
        data class ProjectProfitSummary(
            @SerialName(KEY_CAPITALIZATION_GAIN)
            val capitalizationGain: CapitalizationGain,
            @SerialName(KEY_INSURED_AMOUNT)
            val insuredGain: InsuredGain,
        ) {

            @Serializable
            data class CapitalizationGain(
                @SerialName(KEY_CAPITALIZATION_GAIN_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_CAPITALIZATION_GAIN_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class InsuredGain(
                @SerialName(KEY_INSURED_AMOUNT_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_INSURED_AMOUNT_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

        }

        @Serializable
        data class Capitalization(
            @SerialName(KEY_CAPI)
            val capi: Capi,
            @SerialName(KEY_GAIN_BY_POINTS)
            val gainByPoints: GainByPoints,
            @SerialName(KEY_GAIN)
            val gain: Gain,
            @SerialName(KEY_ENTRIES)
            val entries: Entries,
            @SerialName(KEY_RE_ENTRIES)
            val reEntries: ReEntries,
            @SerialName(KEY_PEGS)
            val pegs: Pegs,
            @SerialName(KEY_CAMPAIGN_PROJECTION_ACTIVE_CONSULTANTS)
            val activesConsultant: ActiveConsultants,
        ) {

            @Serializable
            data class Capi(
                @SerialName(KEY_CAPI_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_CAPI_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class GainByPoints(
                @SerialName(KEY_GAIN_BY_POINTS_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_GAIN_BY_POINTS_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class Gain(
                @SerialName(KEY_GAIN_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_GAIN_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class Entries(
                @SerialName(KEY_ENTRIES_PROJECTED)
                val projected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_ENTRIES_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class ReEntries(
                @SerialName(KEY_RE_ENTRIES_PROJECTED)
                val projected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_RE_ENTRIES_REAL)
                val real: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class Pegs(
                @SerialName(KEY_PEGS_CURRENT)
                val current: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_PEGS_RETENTION_EXPECTED)
                val retentionExpected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_PEGS_LEAVE_EXPECTED)
                val leaveExpected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_PEGS_RETENTION_REAL)
                val retentionReal: Int? = Constant.NUMBER_ZERO,
            )

            @Serializable
            data class ActiveConsultants(
                @SerialName(KEY_ACTIVE_CONSULTANTS_EXPECTED)
                val expected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_ACTIVE_CONSULTANTS_CURRENT_ACTIVES)
                val currentActives: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_ACTIVE_CONSULTANTS_ACTIVES_EXPECTED)
                val activesExpected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_FINALS_LAST_YEAR)
                val finalsLastYearActives: Int? = Constant.NUMBER_ZERO,
            )
        }

        @Serializable
        data class Retention6d6(
            @SerialName(KEY_RETENTION_RE_ENTRIES_LAST_5C)
            val reEntriesLast5C: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_RETENTION_6D6_GAIN_6D6_LOW)
            val retentionGain6d6Low: Float? = Constant.ZERO_FLOAT,
            @SerialName(KEY_RETENTION_6D6_GAIN_6D6_HIGH)
            val retentionGain6d6High: Float? = Constant.ZERO_FLOAT,
            @SerialName(KEY_RETENTION_6D6_GAIN_6D6_HIGH_MULTIBRAND)
            val retentionGain6d6HighMultibrand: Float? = Constant.ZERO_FLOAT,
            @SerialName(KEY_RETENTION_6D6_LIST)
            val retentionList: List<Retention> = emptyList(),
        ) {
            @Serializable
            data class Retention(
                @SerialName(KEY_RETENTION_6D6_LIST_TITLE)
                val title: String? = Constant.EMPTY_STRING,
                @SerialName(KEY_RETENTION_6D6_LIST_ACTIVE_CONSULTANTS)
                val activeConsultants: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_RETENTION_6D6_LIST_RETENTION_REAL)
                val retentionReal: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_RETENTION_6D6_LIST_RETENTION_EXPECTED)
                val retentionExpected: Int? = Constant.NUMBER_ZERO,
            )
        }

        @Serializable
        data class Orders(
            @SerialName(KEY_ORDERS_CURRENT_TOTAL)
            val currentTotal: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_ORDERS_EXPECTED_TOTAL)
            val expectedTotal: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_ORDERS_TOTAL_GAIN)
            val totalGain: Float? = Constant.ZERO_FLOAT,
            @SerialName(KEY_ORDERS_LIST)
            val orderList: List<Order> = emptyList()
        ) {
            @Serializable
            data class Order(
                @SerialName(KEY_ORDER_TITLE)
                val title: String? = Constant.EMPTY_STRING,
                @SerialName(KEY_ORDER_UNITS_EXPECTED)
                val unitsExpected: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_ORDER_UNITS_REAL)
                val unitsReal: Int? = Constant.NUMBER_ZERO,
                @SerialName(KEY_ORDER_GAIN_PER_ORDER)
                val gainPerOrder: Float? = Constant.ZERO_FLOAT,
                @SerialName(KEY_ORDER_GAIN_PER_ORDER_NOT_SUCCESS)
                val gainPerOrderNotSuccess: Float? = Constant.ZERO_FLOAT,
                @SerialName(KEY_ORDER_GAIN_PER_ORDER_EXPECTED)
                val gainPerOrderExpected: Float? = Constant.ZERO_FLOAT,
            )
        }
    }
}
