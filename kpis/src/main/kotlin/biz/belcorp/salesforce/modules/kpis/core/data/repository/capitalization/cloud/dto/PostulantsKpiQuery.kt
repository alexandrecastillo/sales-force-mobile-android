package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class PostulantsKpiQuery(private val request: PostulantsKpiParams) : BaseQuery() {

    override val keyFunctionName =
        FUNCTION_KEY
    override val keyCollection =
        COLLECTION_KEY
    override val keyFilter =
        FILTER_KEY
    override val keyFilterType =
        FILTER_TYPE
    override val keyInput =
        INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_CURRENT_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_NAME)
                field(KEY_IN_EVALUATION)
                field(KEY_PRE_APPROVED)
                field(KEY_APPROVED)
                field(KEY_REJECTED)
                field(KEY_CONVERSION)
                field(KEY_DAYS_ON_HOLD)
                field(KEY_ANTICIPATED_INCOME)
                field(KEY_PRE_REGISTERED)
            }
        }
    }
}
