package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.COLLECTION_EDIT_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.EDIT_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.EDIT_TYPE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.FUNCTION_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.INPUT_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_AMOUNT_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_CAMPAIGN_CREATED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_COMMENTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_CONSULTANT_CODE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DATE_CAMPAIGN_END
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DATE_COMPLETED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DATE_CREATED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DATE_EDITED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DREAM
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_DREAM_ID
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_NUMBER_CAMPAIGNS_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_REGION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_SECTION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_STATUS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.KEY_ZONE

class DreamEditQuery(private val request: DreamEditParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_EDIT_KEY
    override val keyFilter = EDIT_KEY
    override val keyFilterType = EDIT_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        mutation(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_CONSULTANT_CODE)
                field(KEY_AMOUNT_TO_COMPLETE)
                field(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
                field(KEY_COMMENTS)
                field(KEY_DREAM)
                field(KEY_CAMPAIGN_CREATED)
                field(KEY_DATE_CAMPAIGN_END)
                field(KEY_DATE_CREATED)
                field(KEY_DATE_EDITED)
                field(KEY_DATE_COMPLETED)
                field(KEY_STATUS)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_REGION)
                field(KEY_DREAM_ID)
            }
        }
    }
}
