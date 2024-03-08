package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.core.utils.kraph.types.KraphVariable


data class RejectedOrdersQuery(val params: RejectedOrdersRequest) : BaseQuery() {

    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE_KEY
    override val keyFunctionName = FUNCTION_NAME_KEY
    override val keyInput = INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(CAMPAIGN_KEY)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                fieldObject(REASONS_KEY) {
                    field(NAME_KEY)
                    field(QUANTITY_KEY)
                }
            }
        }
    }
}
