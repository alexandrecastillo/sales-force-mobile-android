package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class ProfileCapitalizationQuery(val params: KpiRequestParams) : BaseQuery() {

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
                    field(KEY_ENTRIES)
                    field(KEY_REENTRIES)
                    field(KEY_EXPENSES)
                }
            }
        }
    }
}
