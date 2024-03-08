package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class NewCycleQuery(val params: KpiRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_NAME_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyInput = INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_PROFILE)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                fieldObject(KEY_HIGH_VALUE_ORDERS) {
                    field(KEY_VALUE_ORDERS_I3D3)
                    field(KEY_VALUE_ORDERS_I4D4)
                    field(KEY_VALUE_ORDERS_I5D5)
                    field(KEY_VALUE_ORDERS_I6D6)
                    field(KEY_VALUE_ORDERS_RETENTION_PERCENTAGE)
                }
                fieldObject(KEY_LOW_VALUE_ORDERS) {
                    field(KEY_VALUE_ORDERS_I1D1)
                    field(KEY_VALUE_ORDERS_I2D2)
                    field(KEY_VALUE_ORDERS_I3D3)
                    field(KEY_VALUE_ORDERS_I4D4)
                    field(KEY_VALUE_ORDERS_I5D5)
                    field(KEY_VALUE_ORDERS_I6D6)
                    field(KEY_VALUE_ORDERS_RETENTION_PERCENTAGE)
                }
            }
        }
    }

    @Serializable
    data class Data(
        @SerialName(COLLECTION_KEY)
        val kpiRetention: List<KpiRetention> = emptyList()
    ) {
        @Serializable
        data class KpiRetention(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_PROFILE)
            val profile: String,
            @SerialName(KEY_REGION)
            val region: String? = EMPTY_STRING,
            @SerialName(KEY_SECTION)
            val section: String? = EMPTY_STRING,
            @SerialName(KEY_ZONE)
            val zone: String? = EMPTY_STRING,
            @SerialName(KEY_HIGH_VALUE_ORDERS)
            val highValueOrders: ValueOrders,
            @SerialName(KEY_LOW_VALUE_ORDERS)
            val lowValueOrders: ValueOrders
        ) {
            @Serializable
            data class ValueOrders(
                @SerialName(KEY_VALUE_ORDERS_I1D1)
                val i1d1: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_I2D2)
                val i2d2: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_I3D3)
                val i3d3: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_I4D4)
                val i4d4: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_I5D5)
                val i5d5: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_I6D6)
                val i6d6: Int? = NUMBER_ZERO,
                @SerialName(KEY_VALUE_ORDERS_RETENTION_PERCENTAGE)
                val retentionPercentage: Double = ZERO_DECIMAL
            )
        }
    }

    companion object {
        private const val KEY_HIGH_VALUE_ORDERS = "high_value_orders"
        private const val KEY_LOW_VALUE_ORDERS = "low_value_orders"
        private const val KEY_VALUE_ORDERS_I1D1 = "_1d1"
        private const val KEY_VALUE_ORDERS_I2D2 = "_2d2"
        private const val KEY_VALUE_ORDERS_I3D3 = "_3d3"
        private const val KEY_VALUE_ORDERS_I4D4 = "_4d4"
        private const val KEY_VALUE_ORDERS_I5D5 = "_5d5"
        private const val KEY_VALUE_ORDERS_I6D6 = "_6d6"
        private const val KEY_VALUE_ORDERS_RETENTION_PERCENTAGE = "retention_percentage"
    }
}

