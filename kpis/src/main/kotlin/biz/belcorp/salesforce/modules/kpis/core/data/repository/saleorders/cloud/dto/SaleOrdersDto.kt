package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleOrdersDto(
    @SerialName(COLLECTION_SALE_ORDERS_KEY)
    val saleOrders: List<KpiSaleOrders> = emptyList()
) {
    @Serializable
    data class KpiSaleOrders(
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
        @SerialName(KEY_SALES)
        val sale: Sale,
        @SerialName(KEY_ORDERS)
        val order: Order,
        @SerialName(KEY_ACTIVITY)
        val activity: Activity,
        @SerialName(KEY_ACTIVES)
        val actives: Actives,
        @SerialName(KEY_MULTIBRAND)
        val multibrand: Multibrand?
    ) {
        @Serializable
        data class Sale(
            @SerialName(KEY_NET_SALE_REAL)
            val netSaleReal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_NET_SALE_GOAL)
            val netSaleGoal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_NET_SALES_PERCENTAGE)
            val fulfillmentNetSalesPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_CATALOG_SALE)
            val catalogSale: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_CATALOG_SALE_GOAL)
            val catalogSaleGoal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_CATALOG_SALES_PERCENTAGE)
            val fulfillmentCatalogSalesPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_RANGES)
            val ranges: List<RangeSale> = emptyList()
        ) {
            @Serializable
            data class RangeSale(
                @SerialName(KEY_POS)
                val pos: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_RANGE)
                val range: String = Constant.EMPTY_STRING,
                @SerialName(KEY_AMOUNT)
                val amount: Double = Constant.ZERO_DECIMAL
            )
        }

        @Serializable
        data class Order(
            @SerialName(KEY_REAL)
            val real: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_GOAL)
            val goal: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_HIGH_VALUE_ORDERS)
            val highValueOrders: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_LOW_VALUE_ORDERS)
            val lowValueOrders: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_AVERAGE_AMOUNT)
            val averageAmount: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_AVERAGE_AMOUNT_GOAL)
            val averageAmountGoal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_ORDER_PERCENTAGE)
            val fulfillmentOrderPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_ORDER_AVERAGE_PERCENTAGE)
            val fulfillmentOrderAveragePercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_RANGES)
            var ranges: List<RangeOrder> = emptyList()
        ) {
            @Serializable
            data class RangeOrder(
                @SerialName(KEY_POS)
                var pos: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_RANGE)
                val range: String = Constant.EMPTY_STRING,
                @SerialName(KEY_QUANTITY)
                val quantity: Int = Constant.NUMBER_ZERO
            )
        }

        @Serializable
        data class Activity(
            @SerialName(KEY_PERCENTAGE)
            val percentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_GOAL)
            val goal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_PERCENTAGE)
            val fulfillmentPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_PEGS)
            val pegs: Int = Constant.NUMBER_ZERO
        )

        @Serializable
        data class Actives(
            @SerialName(KEY_INITIALS)
            val initials: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_FINALS)
            val finals: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_FINALS_LAST_YEAR)
            val finalsLastYear: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_RETENTION_PERCENTAGE)
            val retentionPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_RETENTION_FULFILLMENT_PERCENTAGE)
            val fulfillmentRetentionPercentage: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_RETENTION_GOAL)
            val retentionGoal: Double = Constant.ZERO_DECIMAL
        )

        @Serializable
        data class Multibrand(
            @SerialName(KEY_MULTIBRAND_PERCENTAGE)
            val percentage: Double? = Constant.ZERO_DECIMAL,
            @SerialName(KEY_MULTIBRAND_ACTIVES)
            val actives: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_MULTIBRAND_NO_MULTIBRAND_ACTIVES)
            val noMultibrandActives: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_MULTIBRAND_LASTCAMPAIGN)
            val lastCampaign: Double? = Constant.ZERO_DECIMAL,
            @SerialName(KEY_MULTIBRAND_VS_LASTCAMPAIGN)
            val vsLastCampaign: Double? = Constant.ZERO_DECIMAL,
            @SerialName(KEY_MULTIBRAND_NUM_VS_LASTCAMPAIGN)
            val numVsLastCampaign: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_MULTIBRAND_ACTIVES_HIGH_VALUE)
            val activesHighValue: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_MULTIBRAND_ORDERS_HIGH_VALUE)
            val ordersHighValue: Int? = Constant.NUMBER_ZERO,
            @SerialName(KEY_MULTIBRAND_HIGH_VALUE_NUM_VS_LAST_CAMPAIGN)
            val highValueNumVsLastcampaign: Int? = Constant.NUMBER_ZERO,
        )
    }
}
