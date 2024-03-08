package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PostulantsKpiDto(
    @SerialName(COLLECTION_KEY)
    val list: List<PostulantKpi> = emptyList()
) {

    @Serializable
    data class PostulantKpi(
        @SerialName(KEY_CURRENT_CAMPAIGN)
        val currentCampaign: String,
        @SerialName(KEY_REGION)
        val region: String?,
        @SerialName(KEY_ZONE)
        val zone: String?,
        @SerialName(KEY_SECTION)
        val section: String?,
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_IN_EVALUATION)
        val inEvaluation: Int?,
        @SerialName(KEY_PRE_APPROVED)
        val preApproved: Int?,
        @SerialName(KEY_APPROVED)
        val approved: Int?,
        @SerialName(KEY_REJECTED)
        val rejected: Int?,
        @SerialName(KEY_CONVERSION)
        val conversion: Int?,
        @SerialName(KEY_DAYS_ON_HOLD)
        val daysOnHold: Int?,
        @SerialName(KEY_ANTICIPATED_INCOME)
        val anticipatedIncomes: Int?,
        @SerialName(KEY_PRE_REGISTERED)
        val preRegistered: Int?
    )
}
