package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DreamCreateDto(
    @SerialName(COLLECTION_CREATE_KEY)
    val dreamCreated: DreamCreated
) {
    @Serializable
    data class DreamCreated(
        @SerialName(KEY_CONSULTANT_CODE)
        val consultantCode: String? = Constant.EMPTY_STRING,
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
