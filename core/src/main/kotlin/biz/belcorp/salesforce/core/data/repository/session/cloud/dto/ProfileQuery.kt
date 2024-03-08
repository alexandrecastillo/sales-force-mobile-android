package biz.belcorp.salesforce.core.data.repository.session.cloud.dto

import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.core.utils.kraph.types.KraphVariable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault


class ProfileQuery(
    val country: String,
    val role: String,
    val username: String
) {

    companion object {

        private const val OPERATION_NAME = "moperui"

        private const val KEY_PROFILE = "ProfileByUserName"
        private const val KEY_COUNTRY = "country"
        private const val KEY_USERNAME = "userName"
        private const val KEY_ROLE = "role"

        private const val KEY_COD_USER = "cod_user"
        private const val KEY_COD_CONSULTANT = "cod_consultant"
        private const val KEY_CONSULTANT_ID = "consultant_id"
        private const val KEY_FULLNAME = "fullname"
        private const val KEY_NAMES = "names"
        private const val KEY_FIRST_SURNAME = "firstName"
        private const val KEY_SECOND_SURNAME = "lastName"
        private const val KEY_COD_COUNTRY = "cod_country"
        private const val KEY_COUNTRY_NAME = "country_name"
        private const val KEY_COD_REGION = "cod_region"
        private const val KEY_COD_ZONE = "cod_zone"
        private const val KEY_COD_SECTION = "section"
        private const val KEY_COD_ROLE = "cod_role"
        private const val KEY_DOCUMENT = "identification_document"
        private const val KEY_LEVEL = "level"
        private const val KEY_CUB = "cub"
        private const val KEY_LANDLINE = "landline"
        private const val KEY_CELLPHONE = "cellphoneNumber"
        private const val KEY_EMAIL = "email"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
        private const val KEY_CODE_TERRITORY = "cod_territory"

        private const val ARG_INPUT = "input"

        private const val VAR_KEY_PARAMS = "parameters"
        private const val VAR_TYPE_PARAMS = "Parameters"

    }

    fun get(): Kraph {
        return Kraph {
            query(OPERATION_NAME) {
                fieldObject(
                    KEY_PROFILE,
                    mapOf(ARG_INPUT to getInput())
                ) {
                    field(KEY_COD_USER)
                    field(KEY_COD_CONSULTANT)
                    field(KEY_CONSULTANT_ID)
                    field(KEY_FULLNAME)
                    field(KEY_NAMES)
                    field(KEY_FIRST_SURNAME)
                    field(KEY_SECOND_SURNAME)
                    field(KEY_COD_COUNTRY)
                    field(KEY_COUNTRY_NAME)
                    field(KEY_COD_REGION)
                    field(KEY_COD_ZONE)
                    field(KEY_COD_SECTION)
                    field(KEY_COD_ROLE)
                    field(KEY_DOCUMENT)
                    field(KEY_LEVEL)
                    field(KEY_CUB)
                    field(KEY_LANDLINE)
                    field(KEY_CELLPHONE)
                    field(KEY_EMAIL)
                    field(KEY_LATITUDE)
                    field(KEY_LONGITUDE)
                    field(KEY_CODE_TERRITORY)
                }
            }
        }
    }

    private fun Kraph.FieldBuilder.getInput(): KraphVariable {
        val jsonValue = Params(country, role, username).toJson()
        return variable(VAR_KEY_PARAMS, VAR_TYPE_PARAMS, jsonValue)
    }

    @Serializable
    private data class Params(
        @SerialName(KEY_COUNTRY)
        val country: String,
        @SerialName(KEY_ROLE)
        val role: String,
        @SerialName(KEY_USERNAME)
        val username: String
    ) {

        fun toJson(): String {
            return JsonEncodedDefault.encodeToString(serializer(), this)
        }

    }

    @Serializable
    data class Data(
        @SerialName(KEY_PROFILE)
        val profile: List<Profile>
    ) {

        @Serializable
        data class Profile(
            @SerialName(KEY_COD_USER)
            val codUser: String? = null,
            @SerialName(KEY_COD_CONSULTANT)
            val codConsultant: String? = null,
            @SerialName(KEY_CONSULTANT_ID)
            val consultantId: Int? = null,
            @SerialName(KEY_FULLNAME)
            val fullname: String? = null,
            @SerialName(KEY_NAMES)
            val names: String? = null,
            @SerialName(KEY_FIRST_SURNAME)
            val firstSurname: String? = null,
            @SerialName(KEY_SECOND_SURNAME)
            val secondSurname: String? = null,
            @SerialName(KEY_COD_COUNTRY)
            val codCountry: String? = null,
            @SerialName(KEY_COUNTRY_NAME)
            val countryName: String? = null,
            @SerialName(KEY_COD_REGION)
            val codRegion: String? = null,
            @SerialName(KEY_COD_ZONE)
            val codZone: String? = null,
            @SerialName(KEY_COD_SECTION)
            val codSection: String? = null,
            @SerialName(KEY_COD_ROLE)
            val codRole: String? = null,
            @SerialName(KEY_DOCUMENT)
            val document: String? = null,
            @SerialName(KEY_LEVEL)
            val level: String? = null,
            @SerialName(KEY_CUB)
            val cub: String? = null,
            @SerialName(KEY_LANDLINE)
            val landline: String? = null,
            @SerialName(KEY_CELLPHONE)
            val cellphone: String? = null,
            @SerialName(KEY_EMAIL)
            val email: String? = null,
            @SerialName(KEY_LATITUDE)
            val latitude: String? = null,
            @SerialName(KEY_LONGITUDE)
            val longitude: String? = null,
            @SerialName(KEY_CODE_TERRITORY)
            val codTerritory: String? = null
        )

    }

}
