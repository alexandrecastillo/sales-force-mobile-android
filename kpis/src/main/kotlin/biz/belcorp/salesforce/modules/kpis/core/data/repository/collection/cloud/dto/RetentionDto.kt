package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RetentionDto(
    @SerialName(COLLECTION_RETENTION_KEY)
    val retention: List<KpiRetention> = emptyList()
){
    @Serializable
    data class KpiRetention(
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
        @SerialName(HIGH_VALUE_ORDERS_NAME_KEY)
        val highValueOrders: HighValueOrders,
        @SerialName(LOW_VALUE_ORDERS_NAME_KEY)
        val lowValueOrders: LowValueOrders
    ){
        @Serializable
        data class HighValueOrders(
            @SerialName(HIGH_VALUE_ORDERS_3d3_NAME_KEY)
            val _3d3: Double? = Constant.ZERO_DECIMAL,
            @SerialName(HIGH_VALUE_ORDERS_4d4_NAME_KEY)
            val _4d4: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(HIGH_VALUE_ORDERS_5d5_NAME_KEY)
            val _5d5: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(HIGH_VALUE_ORDERS_6d6_NAME_KEY)
            val _6d6: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(HIGH_VALUE_ORDERS_RETENTION_PERCENTAGE_NAME_KEY)
            val retentionPercentage: Double?  = Constant.ZERO_DECIMAL,
        )

        @Serializable
        data class LowValueOrders(
            @SerialName(LOW_VALUE_ORDERS_1d1_NAME_KEY)
            val _1d1: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_2d2_NAME_KEY)
            val _2d2: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_3d3_NAME_KEY)
            val _3d3: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_4d4_NAME_KEY)
            val _4d4: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_5d5_NAME_KEY)
            val _5d5: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_6d6_NAME_KEY)
            val _6d6: Double?  = Constant.ZERO_DECIMAL,
            @SerialName(LOW_VALUE_ORDERS_RETENTION_PERCENTAGE_NAME_KEY)
            val retentionPercentage: Double?  = Constant.ZERO_DECIMAL,
        )
    }
}
