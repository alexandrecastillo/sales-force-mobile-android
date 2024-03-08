package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleConsultantDto(
    @SerialName(COLLECTION_SALE_CONSULTANT_KEY)
    var saleConsultants: List<SaleConsultant> = emptyList()
) {
    @Serializable
    data class SaleConsultant(
        @SerialName(KEY_CAMPAIGN)
        var campaign: String? = EMPTY_STRING,
        @SerialName(KEY_REGION)
        var region: String? = EMPTY_STRING,
        @SerialName(KEY_ZONE)
        var zone: String? = EMPTY_STRING,
        @SerialName(KEY_SECTION)
        var section: String? = EMPTY_STRING,
        @SerialName(KEY_CONSULTANT_CODE)
        var consultantCode: String? = EMPTY_STRING,
        @SerialName(KEY_NET_SALE)
        var netSale: Double? = ZERO_DECIMAL,
        @SerialName(KEY_CATALOG_SALE)
        var catalogSale: Double? = ZERO_DECIMAL,
        @SerialName(KEY_BILLED_ORDER_AMOUNT)
        var billedOrderAmount: Double? = ZERO_DECIMAL,
        @SerialName(KEY_IS_HIGH_VALUE_ORDER)
        var isHighOrderValue: Boolean? = false,
        @SerialName(KEY_BRIGHT_PATH_PERIOD)
        var brightPathPeriod: BrightPeriod? = BrightPeriod(),
        @SerialName(KEY_AVERAGE_SALE_U6C)
        var averageSaleLastSixCampaigns: Double? = ZERO_DECIMAL,
        @SerialName(KEY_GAIN_AMOUNT)
        var gainAmount: Double? = ZERO_DECIMAL,
        @SerialName(KEY_CONSTANT_U6C)
        var constantU6c: Boolean? = false,
        @SerialName(KEY_CURRENT_PACK)
        var currentPack: List<CurrentPack>? = emptyList(),
        @SerialName(KEY_BRIGHT_PATH_PERIOD_)
        var brightPathPeriod_: String? = EMPTY_STRING,
        @SerialName(KEY_BRIGHT_PATH_LEVEL)
        var brightPathLevel: BrightPathLevel? = BrightPathLevel(),
        @SerialName(KEY_BRIGHT_PATH_NEXT_LEVEL)
        var brightPathNextLevel: BrightPathNextLevel? = BrightPathNextLevel(),
    ) {
        @Serializable
        data class BrightPeriod(
            @SerialName(KEY_ORDERS)
            var orders: Int? = NUMBER_ZERO,
            @SerialName(KEY_SALE)
            var sale: Double? = ZERO_DECIMAL
        )

        @Serializable
        data class CurrentPack(
            @SerialName(KEY_CAMPAING)
            var campaign: String? = EMPTY_STRING,
            @SerialName(KEY_BRAND_CODE)
            var brandCode: String? = EMPTY_STRING,
            @SerialName(KEY_BRAND)
            var brand: String? = EMPTY_STRING,
            @SerialName(KEY_AVERAGE)
            var average: Double? = ZERO_DECIMAL,
            @SerialName(KEY_AMOUNT)
            var amount: Int? = NUMBER_ZERO,
        )

        @Serializable
        data class BrightPathLevel(
            @SerialName(KEY_CONSTANT_NAME)
            var name: String? = EMPTY_STRING,
            @SerialName(KEY_ACCUMULATIVE_SALES)
            var accumulativeSales: Double? = ZERO_DECIMAL,
            @SerialName(KEY_ACCUMULATIVE_ORDERS)
            var accumulativeOrders: String? = EMPTY_STRING,
            @SerialName(KEY_HAS_ORDER)
            var hasOrder: Boolean? = false,
            @SerialName(KEY_SALES_REAL)
            var salesReal: Double? = ZERO_DECIMAL,
            @SerialName(KEY_SALES_AVERAGE)
            var salesAverage: Double? = ZERO_DECIMAL,
            @SerialName(KEY_SALES_TO_RETAIN_LEVEL)
            var salesToRetainLevel: Double? = ZERO_DECIMAL,
            @SerialName(KEY_CURRENT_LEVEL_SALES_REQUIREMENT)
            var currentLevelSalesRequirement: Double? = ZERO_DECIMAL,
            @SerialName(KEY_CURRENT_LEVEL_ORDER_REQUIREMENT)
            var currentLevelOrderRequirement: Int? = NUMBER_ZERO,
            @SerialName(KEY_CAMPAIGN_PROGRESS)
            var campaignProgress: Int? = NUMBER_ZERO,
            @SerialName(KEY_AVERAGE_CURRENT_LEVEL)
            var averageCurrentLevel: Double? = ZERO_DECIMAL,
        )

        @Serializable
        data class BrightPathNextLevel(
            @SerialName(KEY_CONSTANT_NAME)
            var name: String? = EMPTY_STRING,
            @SerialName(KEY_SALES)
            var sales: Sales? = Sales(),
            @SerialName(KEY_ORDERS)
            var orders: Orders? = Orders(),
        ) {
            @Serializable
            data class Sales(
                @SerialName(KEY_CONSTANT_NAME)
                var name: String? = EMPTY_STRING,
                @SerialName(KEY_SALES_REQUIREMENT)
                var salesRequirement: Double? = ZERO_DECIMAL,
                @SerialName(KEY_AVERAGE)
                var average: Double? = ZERO_DECIMAL,
            )

            @Serializable
            data class Orders(
                @SerialName(KEY_REQUIREMENT)
                var requirement: Int? = NUMBER_ZERO,
            )
        }
    }
}
