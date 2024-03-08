package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class ProfileSeOrdersU6CQuery(val params: KpiRequestParams) : BaseQuery() {

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
                fieldObject(KEY_ORDERS) {
                    field(KEY_REAL)
                }
                fieldObject(KEY_SALES) {
                    field(KEY_NET_SALE_REAL)
                    field(KEY_NET_SALE_GOAL)
                    field(KEY_FULFILLMENT_NET_SALES_PERCENTAGE)
                }
                fieldObject(KEY_ACTIVES) {
                    field(KEY_FINALS)
                    field(KEY_FINALS_LAST_YEAR)
                }
            }
        }
    }

}
