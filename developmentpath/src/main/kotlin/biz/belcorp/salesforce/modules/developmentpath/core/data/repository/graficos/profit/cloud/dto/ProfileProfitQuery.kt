package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto

import biz.belcorp.salesforce.core.constants.*
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class ProfileProfitQuery(private val params: KpiRequestParams) : BaseQuery() {

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
            }
        }
    }
}
