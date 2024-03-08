package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class ProfitQuery(val params: KpiRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_PROFIT_NAME_KEY
    override val keyFilter = FILTER_PROFIT_KEY
    override val keyFilterType = FILTER_PROFIT_TYPE_KEY
    override val keyCollection = COLLECTION_PROFIT_KEY
    override val keyInput = INPUT_PROFIT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_PROFILE)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_PROF_TOTAL)
                fieldObject(KEY_COMPETITION) {
                    field(KEY_PROF_TOTAL)
                    field(KEY_CAPITALIZATION)
                        fieldObject(KEY_6D6){
                            field(KEY_6D6_LOW_VALUE)
                            field(KEY_6D6_HIGH_VALUE)
                            field(KEY_6D6_TOTAL)
                    }
                    field(KEY_CHANGE_LEVEL)
                    field(KEY_NEW_FIXED)
                    field(KEY_PRODUCTS_RELEASE)
                    fieldObject(KEY_COMPETITION_TACTIC_BONUS){
                        field(KEY_COMPETITION_TACTIC_BONUS_LEVEL)
                        field(KEY_COMPETITION_TACTIC_BONUS_AMOUNT)
                    }
                }
                fieldObject(KEY_PROF_ORDERS) {
                    field(KEY_PROF_TOTAL)
                    field(KEY_POTENTIAL)
                    fieldObject(KEY_PROF_RANGES) {
                        field(KEY_PROF_POS)
                        field(KEY_PROF_RANGE)
                        field(KEY_AMOUNT)
                    }
                }
            }
        }
    }
}
