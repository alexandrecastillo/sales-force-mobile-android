package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsultantDto(
    @SerialName(COLLECTION_KEY)
    val consultants: Consultants
) {

    @Serializable
    data class Consultants(
        @SerialName(KEY_LIST)
        val list: List<Consultant>
    )

    @Serializable
    data class Consultant(
        @SerialName(KEY_ID)
        val id: Int?,
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_CONSULTANT_ID)
        val consultantId: Int?,
        @SerialName(KEY_CODE)
        val code: String?,
        @SerialName(KEY_CHECK_DIGIT)
        val checkDigit: String,
        @SerialName(KEY_MULTIBRAND)
        val multiMarca: Boolean = false,
        @SerialName(KEY_PERSONAL_INFO)
        val personalInfo: PersonalInfo,
        @SerialName(KEY_ANNIVERSARY_DATE)
        val anniversaryDate: String,
        @SerialName(KEY_CAMPAIGN_ADMISSION)
        val campaignAdmission: String,
        @SerialName(KEY_CONSTANCY)
        val constancy: Constancy,
        @SerialName(KEY_SEGMENT)
        val segment: Segment,
        @SerialName(KEY_BRIGHT_SEGMENT)
        val brightSegment: BrightSegment,
        @SerialName(KEY_BRIGHT_PATH)
        val brightPath: BrightPath,
        @SerialName(KEY_STATE)
        val state: State,
        @SerialName(KEY_IS_PEG)
        val isPeg: Boolean,
        @SerialName(KEY_IS_NEW)
        val isNew: Boolean,
        @SerialName(KEY_IS_AUTHORIZED)
        val isAuthorized: Boolean,
        @SerialName(KEY_IS_POTENTIAL_ENTRY)
        val isPotentialEntry: Boolean,
        @SerialName(KEY_IS_POTENTIAL_REENTRY)
        val isPotentialReentry: Boolean,
        @SerialName(KEY_BILLING_INFO)
        val billingInfo: BillingInfo,
        @SerialName(KEY_SALES_INFO)
        val salesInfo: SalesInfo,
        @SerialName(KEY_COLLECTION_INFO)
        val collectionInfo: CollectionInfo,
        @SerialName(KEY_GEO_INFO)
        val geoInfo: GeoInfo,
        @SerialName(KEY_REENTRIES_U18C)
        val reentriesU18C: Int,
        @SerialName(KEY_IS_NEW_INCONSTANT)
        val isNewInconstant: Boolean,
        @SerialName(KEY_DIGITAL)
        val digital: Digital,
        @SerialName(KEY_CASH_PAYMENT)
        val hasCashPayment: Boolean,
        @SerialName(KEY_LAST_MODIFIED)
        val lastModified: String
    )

    @Serializable
    data class PersonalInfo(
        @SerialName(KEY_FULL_NAME)
        val fullName: String,
        @SerialName(KEY_FIRST_NAME)
        val firstName: String,
        @SerialName(KEY_SURNAME)
        val surname: String,
        @SerialName(KEY_SECOND_NAME)
        val secondName: String,
        @SerialName(KEY_SECOND_SURNAME)
        val secondSurname: String,
        @SerialName(KEY_DOCUMENT_NUMBER)
        val documentNumber: String,
        @SerialName(KEY_ADDRESS)
        val address: String,
        @SerialName(KEY_ADDRESS_REFERENCE)
        val addressReference: String,
        @SerialName(KEY_PHONE)
        val phone: String,
        @SerialName(KEY_EMAIL)
        val email: String,
        @SerialName(KEY_BIRTHDAY)
        val birthday: String,
        @SerialName(KEY_CHECK_DIGIT)
        val checkDigit: String
    )

    @Serializable
    data class Constancy(
        @SerialName(KEY_CONSTANCY_U6C)
        val u6c: Boolean,
        @SerialName(KEY_CONSTANCY_U18C)
        val u18c: Boolean,
        @SerialName(KEY_CONSTANCY_NEW)
        val new: String,
        @SerialName(KEY_CONSTANCY_ESTABLISHED)
        val established: String
    )

    @Serializable
    data class Segment(
        @SerialName(KEY_SEGMENT_CODE)
        val code: String,
        @SerialName(KEY_SEGMENT_DESCRIPTION)
        val description: String
    )

    @Serializable
    data class BrightSegment(
        @SerialName(KEY_BRIGHT_SEGMENT_CODE)
        val code: String,
        @SerialName(KEY_BRIGHT_SEGMENT_DESCRIPTION)
        val description: String
    )

    @Serializable
    data class BrightPath(
        @SerialName(KEY_BRIGHT_PATH_PERIOD)
        val period: String,
        @SerialName(KEY_BRIGHT_PATH_SALE)
        val sale: Double,
        @SerialName(KEY_BRIGHT_PATH_LEVEL_CODE)
        val levelCode: String,
        @SerialName(KEY_BRIGHT_PATH_LEVEL_DESCRIPTION)
        val levelDescription: String,
        @SerialName(KEY_BRIGHT_PATH_CONSTANCY)
        val constancy: String
    )

    @Serializable
    data class State(
        @SerialName(KEY_STATE_CODE)
        val code: String,
        @SerialName(KEY_STATE_DESCRIPTION)
        val description: String
    )

    @Serializable
    data class BillingInfo(
        @SerialName(KEY_FIRST_CAMPAIGN)
        val firstCampaign: String,
        @SerialName(KEY_LAST_CAMPAIGN)
        val lastCampaign: String,
        @SerialName(KEY_ORDER_STATUS)
        val orderStatus: Int,
        @SerialName(KEY_BILLING_AMOUNT)
        val amount: Double,
        @SerialName(KEY_ORDER_MTO_AMOUNT)
        val orderMtoAmount: Double
    )

    @Serializable
    data class SalesInfo(
        @SerialName(KEY_CATALOG_SALE)
        val catalogSale: Double,
        @SerialName(KEY_ORDER_RANGE)
        val orderRange: String,
        @SerialName(KEY_IS_ORDER_SENT)
        val isOrderSent: Boolean
    )

    @Serializable
    data class CollectionInfo(
        @SerialName(KEY_PENDING_DEBT)
        val pendingDebt: Double,
        @SerialName(KEY_COLLECTION_PERCENTAGE)
        val percentage: Double
    )

    @Serializable
    data class GeoInfo(
        @SerialName(KEY_LATITUDE)
        val latitude: Double,
        @SerialName(KEY_LONGITUDE)
        val longitude: Double
    )

    @Serializable
    data class Digital(
        @SerialName(KEY_SHARE_CATALOG)
        val shareCatalog: Boolean,
        @SerialName(KEY_SUGGESTED_MESSAGE)
        val suggestedMessage: String?,
        @SerialName(KEY_DIGITAL_SEGMENT)
        val digitalSegment: String?
    )

}

