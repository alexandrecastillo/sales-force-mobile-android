package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class CapitalizationQuery(val params: KpiRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_CAPITALIZATION_NAME_KEY
    override val keyFilter = FILTER_CAPITALIZATION_KEY
    override val keyFilterType = FILTER_CAPITALIZATION_TYPE_KEY
    override val keyCollection = COLLECTION_CAPITALIZATION_KEY
    override val keyInput = INPUT_CAPITALIZATION_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_PROFILE)
                fieldObject(KEY_CAPITALIZATION) {
                    field(KEY_REAL)
                    field(KEY_GOAL)
                    field(KEY_CAPITALIZATION_FULFILLMENT)
                    field(KEY_PROJECTED)
                    field(KEY_ENTRIES)
                    field(KEY_ENTRIES_GOAL)
                    field(KEY_REENTRIES)
                    field(KEY_EXPENSES)
                    field(KEY_PROACTIVE)
                    field(KEY_SUCCESS)
                }
                fieldObject(KEY_PEGS) {
                    field(KEY_REAL)
                    fieldObject(KEY_RETENTION) {
                        field(KEY_GOAL)
                        field(KEY_REAL)
                        field(KEY_PERCENTAGE)
                        field(KEY_REMAINING)
                    }
                }
                fieldObject(KEY_POTENTIAL) {
                    field(KEY_TOTAL)
                    field(KEY_ENTRIES)
                    field(KEY_REENTRIES)
                }
            }
        }
    }
}
