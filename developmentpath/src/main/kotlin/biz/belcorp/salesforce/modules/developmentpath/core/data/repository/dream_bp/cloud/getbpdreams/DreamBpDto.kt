package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.*

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DreamBpDto(
    @SerialName(COLLECTION_KEY)
    val dreamBpList: List<DreamBp> = emptyList()
) {
    @Serializable
    data class DreamBp(
        @SerialName(KEY_DREAM_ID)
        val dreamId: String = Constant.EMPTY_STRING,
        @SerialName(KEY_BP_CODE)
        val bpCode: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_STATUS)
        val status: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_REGION)
        val region: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_AMOUNT_TO_COMPLETE)
        val amountToComplete: Int,
        @SerialName(KEY_NUMBER_CAMPAIGNS_TO_COMPLETE)
        val numberCampaignsToComplete: Int,
        @SerialName(KEY_COMMENTS)
        val comment: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DREAM)
        val dream: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_CAMPAIGN_CREATED)
        val campaignCreated: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_CAMPAIGN_END)
        val campaignEnd: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_CREATED)
        val dateCreated: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_EDITED)
        val dateEdited: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_DATE_COMPLETED)
        val dateCompleted: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_PROGRESS_BY_CAMPAIGNS)
        val progressByCampaigns: ProgressByCampaigns
    ) {
        @Serializable
        data class ProgressByCampaigns(
            @SerialName(KEY_TOTAL_GAIN)
            val totalGain: Float,
            @SerialName(KEY_CONSULTANT_LIST)
            val list: List<BpSale>
        ) {
            @Serializable
            data class BpSale(
                @SerialName(KEY_CAMPAIGN)
                val campaign: String,
                @SerialName(KEY_TOTAL)
                val total: Float
            )
        }
    }
}
