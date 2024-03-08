package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.deletedream

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.*


class DreamDeleteQuery(private val request: DreamDeleteParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        mutation(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_DREAM_ID)
                field(KEY_CONSULTANT_CODE)
                field(KEY_AMOUNT_TO_COMPLETE)
                field(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
                field(KEY_COMMENTS)
                field(KEY_DREAM)
                field(KEY_REGION)
                field(KEY_SECTION)
                field(KEY_ZONE)
                field(KEY_CAMPAIGN_CREATED)
                field(KEY_STATUS)
                field(KEY_DATE_CREATED)
                field(KEY_DATE_EDITED)
                field(KEY_DATE_COMPLETED)
                field(KEY_DATE_CAMPAIGN_END)
            }
        }
    }
}
