package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileSeOrdersU6CDto(
    @SerialName(COLLECTION_SALE_ORDERS_KEY)
    val saleOrders: List<ProfileSeOrdersU6C> = emptyList()
) {
    @Serializable
    data class ProfileSeOrdersU6C(
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
        @SerialName(KEY_ACTIVES)
        val actives: Actives
    ) {

        @Serializable
        data class Order(
            @SerialName(KEY_REAL)
            val real: Int = Constant.NUMBER_ZERO
        )

        @Serializable
        data class Sale(
            @SerialName(KEY_NET_SALE_REAL)
            val netSaleReal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_NET_SALE_GOAL)
            val netSaleGoal: Double = Constant.ZERO_DECIMAL,
            @SerialName(KEY_FULFILLMENT_NET_SALES_PERCENTAGE)
            val fulfillmentNetSalesPercentage: Double = Constant.ZERO_DECIMAL
        )

        @Serializable
        data class Actives(
            @SerialName(KEY_FINALS)
            val finals: Int = Constant.NUMBER_ZERO,
            @SerialName(KEY_FINALS_LAST_YEAR)
            val finalsLastYear: Int = Constant.NUMBER_ZERO
        )

    }
}
