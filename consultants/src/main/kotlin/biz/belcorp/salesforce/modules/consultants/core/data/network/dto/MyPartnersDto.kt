package biz.belcorp.salesforce.modules.consultants.core.data.network.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


internal const val COLLECTION_NEW_BUSINESS_PARTNER_KEY = "NewBusinessPartner"

internal const val KEY_CONSULTANT_ID = "consultant_id"
internal const val KEY_CONSULTANT_CODE = "code"
internal const val KEY_ANNIVERSARY_DATE = "anniversary_date"
internal const val KEY_ADMISSION_CAMPAIGN = "admission_campaign"

internal const val KEY_PERSONAL_INFO = "personal_info"

internal const val KEY_FULL_NAME = "full_name"
internal const val KEY_FIRST_NAME = "first_name"
internal const val KEY_SURE_NAME = "surname"
internal const val KEY_SECOND_NAME = "second_name"
internal const val KEY_SECOND_SURE_NAME = "second_surname"
internal const val KEY_DOCUMENT_NUMBER = "document_number"
internal const val KEY_EMAIL = "email"
internal const val KEY_ADDRESS = "address"
internal const val KEY_CELL_PHONE_NUMBER = "cellphone_number"
internal const val KEY_TELEPHONE_NUMBER = "telephone_number"
internal const val KEY_BIRTHDAY = "birthday"

internal const val KEY_PENDING_DEBT = "pending_debt"
internal const val KEY_PRODUCTIVITY = "productivity"
internal const val KEY_BILLING_INFO = "billing_info"
internal const val KEY_IS_SUCCESSFUL = "is_successful"
internal const val KEY_IS_SUCCESSFUL_HISTORY = "is_successful_history"

internal const val KEY_FIRST_CAMPAIGN = "first_campaign"
internal const val KEY_LAST_CAMPAIGN = "last_campaign"

internal const val KEY_LEVEL_CODE = "level"

internal const val KEY_CODE_CODE = "code"
internal const val KEY_NAME_CODE = "name"

internal const val KEY_NEXT_LEVEL_CODE = "next_level"
internal const val KEY_ACCOMPLISHED_CODE = "accomplished"
internal const val KEY_CAMPAIGNS_ACCOMPLISHED_CODE = "campaigns_accomplished"

internal const val KEY_SALES_CODE = "sales"
internal const val KEY_REQUIREMENT_CODE = "requirement"
internal const val KEY_REAL_CODE = "real"

internal const val KEY_ORDERS_CODE = "orders"

internal const val KEY_VALUE = "value"


@Serializable
data class MyPartnersDto(
    @SerialName(COLLECTION_NEW_BUSINESS_PARTNER_KEY)
    val newBusinessPartner: List<NewBusinessPartner> = emptyList()
) {
    @Serializable
    data class NewBusinessPartner(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String = Constant.EMPTY_STRING,
        @SerialName(KEY_REGION)
        val region: String = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String = Constant.EMPTY_STRING,
        @SerialName(KEY_CONSULTANT_ID)
        val consultantId: Int = NUMERO_CERO,
        @SerialName(KEY_CONSULTANT_CODE)
        val consultantCode: String = Constant.EMPTY_STRING,
        @SerialName(KEY_ANNIVERSARY_DATE)
        val anniversaryDate: String = Constant.EMPTY_STRING,
        @SerialName(KEY_ADMISSION_CAMPAIGN)
        val admissionCampaign: String = Constant.EMPTY_STRING,

        @SerialName(KEY_PERSONAL_INFO)
        val personalInfo: PersonalInfo = PersonalInfo(),


        @SerialName(KEY_LEVEL_CODE)
        val level: Level = Level(),

        @SerialName(KEY_NEXT_LEVEL_CODE)
        val nextLevel: NextLevel = NextLevel(),

        @SerialName(KEY_PENDING_DEBT)
        val pendingDebt: Double = Constant.EMPTY_DOUBLE,

        @SerialName(KEY_PRODUCTIVITY)
        val productivity: String = Constant.EMPTY_STRING,

        @SerialName(KEY_BILLING_INFO)
        val billingInfo: BillingInfo = BillingInfo(),

        @SerialName(KEY_IS_SUCCESSFUL)
        val isSuccessful: Boolean = false,

        @SerialName(KEY_IS_SUCCESSFUL_HISTORY)
        val isSuccessfulHistory: List<IsSuccessfulHistory> = emptyList(),

        ) {

        @Serializable
        data class PersonalInfo(
            @SerialName(KEY_FULL_NAME)
            val fullName: String = Constant.EMPTY_STRING,
            @SerialName(KEY_FIRST_NAME)
            val firstName: String = Constant.EMPTY_STRING,
            @SerialName(KEY_SURE_NAME)
            val surname: String = Constant.EMPTY_STRING,
            @SerialName(KEY_SECOND_NAME)
            val secondName: String = Constant.EMPTY_STRING,
            @SerialName(KEY_SECOND_SURE_NAME)
            val secondSurname: String = Constant.EMPTY_STRING,
            @SerialName(KEY_DOCUMENT_NUMBER)
            val documentNumber: String = Constant.EMPTY_STRING,
            @SerialName(KEY_EMAIL)
            val email: String = Constant.EMPTY_STRING,
            @SerialName(KEY_ADDRESS)
            val address: String = Constant.EMPTY_STRING,
            @SerialName(KEY_CELL_PHONE_NUMBER)
            val cellphoneNumber: String = Constant.EMPTY_STRING,
            @SerialName(KEY_TELEPHONE_NUMBER)
            val telephoneNumber: String = Constant.EMPTY_STRING,
            @SerialName(KEY_BIRTHDAY)
            val birthday: String = Constant.EMPTY_STRING,
        )

        @Serializable
        data class Level(
            @SerialName(KEY_NAME_CODE)
            val name: String = Constant.EMPTY_STRING,
            @SerialName(KEY_CODE_CODE)
            val code: String = Constant.EMPTY_STRING,
        )

        @Serializable
        data class NextLevel(
            @SerialName(KEY_SALES_CODE)
            val sales: Sales? = Sales(),
            @SerialName(KEY_ORDERS_CODE)
            val orders: Orders? = Orders(),
            @SerialName(KEY_NAME_CODE)
            val name: String? = Constant.EMPTY_STRING,
            @SerialName(KEY_ACCOMPLISHED_CODE)
            val accomplished: Boolean? = false,
            @SerialName(KEY_CAMPAIGNS_ACCOMPLISHED_CODE)
            val campaignsAccomplished: Int? = Constant.NUMBER_ZERO,

            ) {
            @Serializable
            data class Sales(
                @SerialName(KEY_REQUIREMENT_CODE)
                val requirement: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_REAL_CODE)
                val real: Double = Constant.ZERO_DECIMAL,
                @SerialName(KEY_ACCOMPLISHED_CODE)
                val accomplished: Boolean = false,
            )

            @Serializable
            data class Orders(
                @SerialName(KEY_REQUIREMENT_CODE)
                val requirement: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_REAL_CODE)
                val real: Int = Constant.NUMBER_ZERO,
                @SerialName(KEY_ACCOMPLISHED_CODE)
                val accomplished: Boolean = false,
            )
        }

        @Serializable
        data class BillingInfo(
            @SerialName(KEY_FIRST_CAMPAIGN)
            val firstCampaign: String = Constant.EMPTY_STRING,
            @SerialName(KEY_LAST_CAMPAIGN)
            val lastCampaign: String = Constant.EMPTY_STRING
        )

        @Serializable
        data class IsSuccessfulHistory(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String = Constant.EMPTY_STRING,
            @SerialName(KEY_VALUE)
            val value: Boolean = false
        )

    }


}
