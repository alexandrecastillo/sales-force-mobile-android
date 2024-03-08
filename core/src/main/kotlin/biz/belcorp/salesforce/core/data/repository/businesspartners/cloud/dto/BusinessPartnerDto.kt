package biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusinessPartnerDto(
    @SerialName(KEY_BUSINESS_PARTNERS)
    val businessPartners: List<BusinessPartner>
) {

    @Serializable
    data class BusinessPartner(
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_CODE)
        val code: String,
        @SerialName(KEY_ID)
        val consultantId: Int?,
        @SerialName(KEY_ANNIVERSARY_DATE)
        var anniversaryDate: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_CAMPAIGN_ADMISSION)
        val campaignAdmission: String,
        @SerialName(KEY_PERSONAL_INFO)
        val personalInfo: PersonalInfo,
        @SerialName(KEY_LEVEL)
        val level: Level,
        @SerialName(KEY_PENDING_DEBT)
        val pendingDebt: Double,
        @SerialName(KEY_PRODUCTIVITY)
        val productivity: String? = null,
        @SerialName(KEY_BILLING_INFO)
        val billingInfo: BillingInfo,
        @SerialName(KEY_STATUS)
        val status: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_LEADER_CLASSIFICATION)
        val leaderClassification: String? = Constant.EMPTY_STRING,
        @SerialName(KEY_SUCCESSFUL)
        val successful: Boolean = false,
        @SerialName(KEY_SUCCESSFUL_HISTORIC)
        val successfulHistoric: List<SuccessfulHistoric> = emptyList()
    )

    @Serializable
    data class PersonalInfo(
        @SerialName(KEY_DOCUMENT)
        val document: String,
        @SerialName(KEY_FULL_NAME)
        val fullName: String,
        @SerialName(KEY_FIRST_NAME)
        val firstName: String,
        @SerialName(KEY_SECOND_NAME)
        val secondName: String,
        @SerialName(KEY_SURNAME)
        val firstSurname: String,
        @SerialName(KEY_SECOND_SURNAME)
        val secondSurname: String,
        @SerialName(KEY_EMAIL)
        val email: String,
        @SerialName(KEY_ADDRESS)
        val address: String,
        @SerialName(KEY_CELLPHONE)
        val cellphone: String,
        @SerialName(KEY_HOMEPHONE)
        val homephone: String,
        @SerialName(KEY_BIRTH_DATE)
        var birthDate: String? = Constant.EMPTY_STRING
    )

    @Serializable
    data class Level(
        @SerialName(KEY_LEVEL_CODE)
        val levelCode: String,
        @SerialName(KEY_LEVEL_NAME)
        val levelName: String
    )

    @Serializable
    data class BillingInfo(
        @SerialName(KEY_BILLING_FIRST_CAMPAIGN)
        val billingFirstCampaign: String,
        @SerialName(KEY_BILLING_LAST_CAMPAIGN)
        val billingLastCampaign: String
    )

    @Serializable
    data class SuccessfulHistoric(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_SUCCESSFUL_VALUE)
        val value: Boolean
    )

}
