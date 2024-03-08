package biz.belcorp.salesforce.core.data.repository.directory.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.KEY_PROFILE
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerDirectoryDto(
    @SerialName(KEY_SALES_FORCE_DIRECTORY)
    val directory: List<ManagerDirectory>
) {
    @Serializable
    data class ManagerDirectory(
        @SerialName(KEY_CONSULTANT_ID)
        val consultantId: Int,
        @SerialName(KEY_PROFILE)
        val profile: String,
        @SerialName(KEY_REGION)
        val region: String? = EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String? = EMPTY_STRING,
        @SerialName(KEY_USERNAME)
        val userName: String,
        @SerialName(KEY_PERSONAL_INFO)
        val personalInfo: Info,
        @SerialName(KEY_PRODUCTIVITY)
        val productivity: String,
        @SerialName(KEY_STATE)
        val state: State,
        @SerialName(KEY_IS_NEW)
        val isNew: Boolean,
        @SerialName(KEY_CAMPAIGNS_AS_NEW)
        val campaignsAsNew: Int
    )

    @Serializable
    data class Info(
        @SerialName(KEY_FIRST_NAME)
        val firstName: String,
        @SerialName(KEY_SECOND_NAME)
        val secondName: String,
        @SerialName(KEY_SURNAME)
        val surname: String,
        @SerialName(KEY_SECOND_SURNAME)
        val secondSurname: String,
        @SerialName(KEY_FULL_NAME)
        val fullName: String,
        @SerialName(KEY_DOCUMENT)
        val document: String,
        @SerialName(KEY_EMAIL)
        val email: String,
        @SerialName(KEY_TELEPHONE_NUMBER)
        val telephoneNumber: String,
        @SerialName(KEY_CELLPHONE_NUMBER)
        val cellphoneNumber: String,
        @SerialName(KEY_BIRTHDAY)
        val birthday: String
    )

    @Serializable
    data class State(
        @SerialName(KEY_CODE)
        val code: String,
        @SerialName(KEY_DESCRIPTION)
        val description: String
    )
}
