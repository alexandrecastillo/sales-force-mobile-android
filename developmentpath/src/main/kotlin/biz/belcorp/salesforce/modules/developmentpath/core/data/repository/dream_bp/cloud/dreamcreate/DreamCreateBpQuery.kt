package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.COLLECTION_CREATE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.CREATE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.CREATE_TYPE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.FUNCTION_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.INPUT_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_AMOUNT_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_BP_CODE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_CAMPAIGN_CREATED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_COMMENTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DATE_CAMPAIGN_END
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DATE_COMPLETED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DATE_EDITED
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DREAM
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_DREAM_ID
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_NUMBER_CAMPAIGNS_TO_COMPLETE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_REGION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_SECTION
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_STATUS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.KEY_ZONE


class DreamCreateBpQuery(private val request: DreamCreateBpParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_CREATE_KEY
    override val keyFilter = CREATE_KEY
    override val keyFilterType = CREATE_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        mutation(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_BP_CODE)
                field(KEY_AMOUNT_TO_COMPLETE)
                field(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
                field(KEY_COMMENTS)
                field(KEY_DREAM)
                field(KEY_CAMPAIGN_CREATED)
                field(KEY_DATE_CAMPAIGN_END)
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
