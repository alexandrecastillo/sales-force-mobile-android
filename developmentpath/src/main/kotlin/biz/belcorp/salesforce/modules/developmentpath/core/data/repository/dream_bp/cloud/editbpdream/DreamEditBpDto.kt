package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.COLLECTION_UPDATE_KEY
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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DreamEditBpDto(
    @SerialName(COLLECTION_UPDATE_KEY)
    val dreamEdited: DreamBusinessPartnerEdited
) {
    @Serializable
    data class DreamBusinessPartnerEdited(
        @SerialName(KEY_BP_CODE)
        val bpCode: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_AMOUNT_TO_COMPLETE)
        val amountToComplete: Long = 0,
        @SerialName(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
        val numberCampaignsToComplete: Int = 0,
        @SerialName(KEY_COMMENTS)
        val comment: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DREAM)
        val dream: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_CAMPAIGN_CREATED)
        val campaignCreated: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_CAMPAIGN_END)
        val campaignEnd: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_EDITED)
        val dateEdited: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_COMPLETED)
        val dateCompleted: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_STATUS)
        val status: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_REGION)
        val region: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DREAM_ID)
        val dreamId: String? = Constant.EMPTY_STRING,
    )
}
