package biz.belcorp.salesforce.modules.brightpath.core.data.network.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_PROFILE
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


internal const val FILTER_BUSINESS_PARTNER_CHANGE_LEVEL_KEY = "FilterBPCL"
internal const val FILTER_BUSINESS_PARTNER_CHANGE_LEVEL_TYPE_KEY = "FilterBPCL"
internal const val COLLECTION_BUSINESS_PARTNER_CHANGE_LEVEL_KEY = "BusinessPartnerChangeLevel"
internal const val INPUT_KEY = "input"


internal const val KEY_CONSULTANT_CODE = "consultant_code"


internal const val KEY_LEVEL_CODE = "level"

internal const val KEY_CODE_CODE = "code"
internal const val KEY_NAME_CODE = "name"
internal const val KEY_CONSECUTIVE_CAMPAIGNS_CODE = "consecutive_campaigns"
internal const val KEY_CAMPAIGNS_NOT_ACCOMPLISHED_CODE = "campaigns_not_accomplished"

internal const val KEY_NEXT_LEVEL_CODE = "next_level"
internal const val KEY_ACCOMPLISHED_CODE = "accomplished"
internal const val KEY_CAMPAIGNS_ACCOMPLISHED_CODE = "campaigns_accomplished"

internal const val KEY_SALES_CODE = "sales"
internal const val KEY_REQUIREMENT_CODE = "requirement"
internal const val KEY_REAL_CODE = "real"

internal const val KEY_ORDERS_CODE = "orders"

internal const val KEY_LEVEL_REQUIREMENT_CODE = "level_requirement"

internal const val KEY_MINIMUM_SALES_CODE = "minimum_sales"
internal const val KEY_MINIMUM_ORDERS_CODE = "minimum_orders"

internal const val KEY_CAMPAIGN_REQUIREMENT_CODE = "campaign_requirement"

internal const val KEY_SECTION_SALES_CODE = "section_sales"
internal const val KEY_SECTION_ORDERS_CODE = "section_orders"
internal const val KEY_GAIN_AMOUNT_LOW_VALUE_CODE = "gain_amount_low_value"
internal const val KEY_GAIN_AMOUNT_LOW_VALUE_PLUS_CODE = "gain_amount_low_value_plus"
internal const val KEY_GAIN_AMOUNT_HIGH_VALUE_CODE = "gain_amount_high_value"
internal const val KEY_GAIN_AMOUNT_HIGH_VALUE_PLUS_CODE = "gain_amount_high_value_plus"


@Serializable
data class BusinessPartnerChangeLevelDto(
    @SerialName(COLLECTION_BUSINESS_PARTNER_CHANGE_LEVEL_KEY)
    val businessPartnerChangeLevel: List<BusinessPartnerChangeLevel> = emptyList()
) {
    @Serializable
    data class BusinessPartnerChangeLevel(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String = Constant.EMPTY_STRING,
        @SerialName(KEY_PROFILE)
        val profile: String = Constant.EMPTY_STRING,
        @SerialName(KEY_REGION)
        val region: String = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String = Constant.EMPTY_STRING,
        @SerialName(KEY_CONSULTANT_CODE)
        val consultantCode: String = Constant.EMPTY_STRING,
        @SerialName(KEY_LEVEL_CODE)
        val level: Level = Level(),
        @SerialName(KEY_NEXT_LEVEL_CODE)
        val nextLevel: NextLevel = NextLevel(),
        @SerialName(KEY_LEVEL_REQUIREMENT_CODE)
        val levelRequirement: List<LevelRequirement> = emptyList(),
        @SerialName(KEY_CAMPAIGN_REQUIREMENT_CODE)
        val campaignRequirement: Int = Constant.NUMBER_ZERO,

        @SerialName(KEY_SECTION_SALES_CODE)
        val sectionSales: Double = Constant.EMPTY_DOUBLE,
        @SerialName(KEY_SECTION_ORDERS_CODE)
        val sectionOrders: Int? = Constant.NUMBER_ZERO,
        @SerialName(KEY_GAIN_AMOUNT_LOW_VALUE_CODE)
        val gainAmountLowValue: Double = Constant.EMPTY_DOUBLE,
        @SerialName(KEY_GAIN_AMOUNT_LOW_VALUE_PLUS_CODE)
        val gainAmountLowValuePlus: Double = Constant.EMPTY_DOUBLE,
        @SerialName(KEY_GAIN_AMOUNT_HIGH_VALUE_CODE)
        val gainAmountHighValue: Double = Constant.EMPTY_DOUBLE,
        @SerialName(KEY_GAIN_AMOUNT_HIGH_VALUE_PLUS_CODE)
        val gainAmountHighValuePlus: Double = Constant.EMPTY_DOUBLE,
    ) {

        @Serializable
        data class Level(
            @SerialName(KEY_CODE_CODE)
            val code: String = Constant.EMPTY_STRING,
            @SerialName(KEY_NAME_CODE)
            val name: String = Constant.EMPTY_STRING,
            @SerialName(KEY_CONSECUTIVE_CAMPAIGNS_CODE)
            val consecutiveCampaigns: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_CAMPAIGNS_NOT_ACCOMPLISHED_CODE)
            val campaignsNotAccomplished: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_ACCOMPLISHED_CODE)
            val accomplished: Boolean = false,
        )

        @Serializable
        data class NextLevel(
            @SerialName(KEY_NAME_CODE)
            val name: String = Constant.EMPTY_STRING,
            @SerialName(KEY_ACCOMPLISHED_CODE)
            val accomplished: Boolean = false,
            @SerialName(KEY_CAMPAIGNS_ACCOMPLISHED_CODE)
            val campaigns_accomplished: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_SALES_CODE)
            val sales: Sales = Sales(),
            @SerialName(KEY_ORDERS_CODE)
            val orders: Orders = Orders(),
        ) {
            @Serializable
            data class Sales(
                @SerialName(KEY_REQUIREMENT_CODE)
                val requirement: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_REAL_CODE)
                val real: Float = Constant.ZERO_FLOAT,
                @SerialName(KEY_ACCOMPLISHED_CODE)
                val accomplished: Boolean = false,
            )

            @Serializable
            data class Orders(
                @SerialName(KEY_REQUIREMENT_CODE)
                val requirement: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_REAL_CODE)
                val real: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_ACCOMPLISHED_CODE)
                val accomplished: Boolean = false,
            )
        }

        @Serializable
        data class LevelRequirement(
            @SerialName(KEY_LEVEL_CODE)
            val level: String = Constant.EMPTY_STRING,
            @SerialName(KEY_MINIMUM_SALES_CODE)
            val minimumSales: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_MINIMUM_ORDERS_CODE)
            val minimumOrders: Int = Constant.NUMBER_ZERO,

            )

    }


}
