package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class CollectionQuery(val params: CollectionRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_COLLECTION_NAME_KEY
    override val keyFilter = FILTER_COLLECTION_KEY
    override val keyFilterType = FILTER_COLLECTION_TYPE_KEY
    override val keyCollection = COLLECTION_CHARGE_KEY
    override val keyInput = INPUT_COLLECTION_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_PROFILE)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_DAYS)
                field(KEY_PERCENTAGE)
                field(KEY_INVOICED_SALE)
                field(KEY_AMOUNT_COLLECTED)
                field(KEY_DEBTOR_CONSULTANTS)
                fieldObject(KEY_ORDERS) {
                    field(KEY_TOTAL_GAINED)
                    field(KEY_MINIMAL_COLLECTION_PERCENTAGE)
                    field(KEY_TOTAL_COLLECTED)
                    field(KEY_TOTAL_ORDERS)
                    fieldObject(KEY_RANGES) {
                        field(KEY_POS)
                        field(KEY_RANGE)
                        field(KEY_COLLECTED)
                        field(KEY_TOTAL)
                    }
                }
            }
        }
    }
}
