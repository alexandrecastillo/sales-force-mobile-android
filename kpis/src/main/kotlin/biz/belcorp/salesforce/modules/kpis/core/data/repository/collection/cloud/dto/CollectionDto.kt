package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CollectionDto(
    @SerialName(COLLECTION_CHARGE_KEY)
    val kpiCollection: List<KpiCollection> = emptyList()
) {
    @Serializable
    data class KpiCollection(
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
        @SerialName(KEY_DAYS)
        val days: String? = EMPTY_STRING,
        @SerialName(KEY_PERCENTAGE)
        val percentage: Double = ZERO_DECIMAL,
        @SerialName(KEY_INVOICED_SALE)
        var invoicedSale: Double = ZERO_DECIMAL,
        @SerialName(KEY_AMOUNT_COLLECTED)
        var amountCollected: Double = ZERO_DECIMAL,
        @SerialName(KEY_DEBTOR_CONSULTANTS)
        var debtorConsultants: Int = NUMBER_ZERO,
        @SerialName(KEY_ORDERS)
        val orders: Orders
    ) {

        @Serializable
        data class Orders(
            @SerialName(KEY_TOTAL_GAINED)
            val totalGained: Double = ZERO_DECIMAL,
            @SerialName(KEY_MINIMAL_COLLECTION_PERCENTAGE)
            val minimalCollectionPercentage: Double = ZERO_DECIMAL,
            @SerialName(KEY_TOTAL_COLLECTED)
            val totalCollected: Int = NUMBER_ZERO,
            @SerialName(KEY_TOTAL_ORDERS)
            val totalOrders: Int = NUMBER_ZERO,
            @SerialName(KEY_RANGES)
            val ranges: List<Range> = emptyList()
        )

        @Serializable
        data class Range(
            @SerialName(KEY_POS)
            val pos: Int = NUMBER_ZERO,
            @SerialName(KEY_RANGE)
            val range: String = EMPTY_STRING,
            @SerialName(KEY_COLLECTED)
            val collected: Int = NUMBER_ZERO,
            @SerialName(KEY_TOTAL)
            val total: Int = NUMBER_ZERO
        )
    }
}
