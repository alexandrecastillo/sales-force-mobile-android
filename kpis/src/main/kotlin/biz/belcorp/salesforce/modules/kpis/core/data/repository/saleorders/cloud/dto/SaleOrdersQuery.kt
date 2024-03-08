package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class SaleOrdersQuery(val params: KpiRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_SALE_ORDERS_NAME_KEY
    override val keyFilter = FILTER_SALE_ORDERS_KEY
    override val keyFilterType = FILTER_SALE_ORDERS_TYPE_KEY
    override val keyCollection = COLLECTION_SALE_ORDERS_KEY
    override val keyInput = INPUT_SALE_ORDERS_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_PROFILE)
                fieldObject(KEY_SALES) {
                    field(KEY_NET_SALE_REAL)
                    field(KEY_NET_SALE_GOAL)
                    field(KEY_FULFILLMENT_NET_SALES_PERCENTAGE)
                    field(KEY_CATALOG_SALE)
                    field(KEY_CATALOG_SALE_GOAL)
                    field(KEY_FULFILLMENT_CATALOG_SALES_PERCENTAGE)
                    fieldObject(KEY_RANGES) {
                        field(KEY_POS)
                        field(KEY_RANGE)
                        field(KEY_AMOUNT)
                    }
                }
                fieldObject(KEY_ORDERS) {
                    field(KEY_REAL)
                    field(KEY_GOAL)
                    field(KEY_HIGH_VALUE_ORDERS)
                    field(KEY_LOW_VALUE_ORDERS)
                    field(KEY_AVERAGE_AMOUNT)
                    field(KEY_AVERAGE_AMOUNT_GOAL)
                    field(KEY_FULFILLMENT_ORDER_PERCENTAGE)
                    field(KEY_FULFILLMENT_ORDER_AVERAGE_PERCENTAGE)
                    fieldObject(KEY_RANGES) {
                        field(KEY_POS)
                        field(KEY_RANGE)
                        field(KEY_QUANTITY)
                    }
                }
                fieldObject(KEY_ACTIVITY) {
                    field(KEY_PERCENTAGE)
                    field(KEY_GOAL)
                    field(KEY_FULFILLMENT_PERCENTAGE)
                    field(KEY_PEGS)
                }
                fieldObject(KEY_ACTIVES) {
                    field(KEY_INITIALS)
                    field(KEY_FINALS)
                    field(KEY_FINALS_LAST_YEAR)
                    field(KEY_RETENTION_PERCENTAGE)
                    field(KEY_RETENTION_FULFILLMENT_PERCENTAGE)
                    field(KEY_RETENTION_GOAL)
                }
                fieldObject(KEY_MULTIBRAND) {
                    field(KEY_MULTIBRAND_PERCENTAGE)
                    field(KEY_MULTIBRAND_ACTIVES)
                    field(KEY_MULTIBRAND_NO_MULTIBRAND_ACTIVES)
                    field(KEY_MULTIBRAND_LASTCAMPAIGN)
                    field(KEY_MULTIBRAND_VS_LASTCAMPAIGN)
                    field(KEY_MULTIBRAND_NUM_VS_LASTCAMPAIGN)
                    field(KEY_MULTIBRAND_ACTIVES_HIGH_VALUE)
                    field(KEY_MULTIBRAND_ORDERS_HIGH_VALUE)
                    field(KEY_MULTIBRAND_HIGH_VALUE_NUM_VS_LAST_CAMPAIGN)
                }
            }
        }
    }
}
