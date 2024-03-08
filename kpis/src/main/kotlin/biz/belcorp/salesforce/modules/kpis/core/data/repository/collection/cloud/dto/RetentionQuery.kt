package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class RetentionQuery(val params: KpiRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_RETENTION_NAME_KEY
    override val keyFilter = FILTER_RETENTION_KEY
    override val keyFilterType = FILTER_RETENTION_TYPE_KEY
    override val keyCollection = COLLECTION_RETENTION_KEY
    override val keyInput = INPUT_RETENTION_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_PROFILE)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)

                fieldObject(HIGH_VALUE_ORDERS_NAME_KEY) {
                    field(HIGH_VALUE_ORDERS_3d3_NAME_KEY)
                    field(HIGH_VALUE_ORDERS_4d4_NAME_KEY)
                    field(HIGH_VALUE_ORDERS_5d5_NAME_KEY)
                    field(HIGH_VALUE_ORDERS_6d6_NAME_KEY)
                    field(HIGH_VALUE_ORDERS_RETENTION_PERCENTAGE_NAME_KEY)
                }

                fieldObject(LOW_VALUE_ORDERS_NAME_KEY) {
                    field(LOW_VALUE_ORDERS_1d1_NAME_KEY)
                    field(LOW_VALUE_ORDERS_2d2_NAME_KEY)
                    field(LOW_VALUE_ORDERS_3d3_NAME_KEY)
                    field(LOW_VALUE_ORDERS_4d4_NAME_KEY)
                    field(LOW_VALUE_ORDERS_5d5_NAME_KEY)
                    field(LOW_VALUE_ORDERS_6d6_NAME_KEY)
                    field(LOW_VALUE_ORDERS_RETENTION_PERCENTAGE_NAME_KEY)
                }
            }
        }
    }

}
