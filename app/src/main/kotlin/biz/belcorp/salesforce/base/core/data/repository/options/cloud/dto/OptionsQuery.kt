package biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto

import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.core.utils.kraph.types.KraphVariable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class OptionsQuery(
    val country: String,
    val role: String,
    val region: String,
    val zone: String,
    val section: String,
    val application: String = ORIGIN
) {

    companion object {

        private const val ORIGIN = "App"

        private const val OPERATION_NAME = "option"

        private const val KEY_OPTIONS = "Options"
        private const val KEY_COUNTRY = "country"
        private const val KEY_APP = "application"
        private const val KEY_REGION = "region"
        private const val KEY_ZONE = "zone"
        private const val KEY_SECTION = "section"
        private const val KEY_OPTION_CODE = "option_code"
        private const val KEY_OPTION_TYPE = "option_type"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_POSITION = "position"
        private const val KEY_URL = "url"
        private const val KEY_ROLE = "role"
        private const val KEY_ACTIVE = "active"
        private const val KEY_SUB_OPTION = "sub_option"

        private const val ARG_INPUT = "input"

        private const val VAR_KEY_PARAMS = "params"
        private const val VAR_TYPE_PARAMS = "Params"

    }

    fun get(): Kraph {
        return Kraph {
            query(OPERATION_NAME) {
                fieldObject(
                    KEY_OPTIONS,
                    mapOf(ARG_INPUT to getInput())
                ) {
                    field(KEY_OPTION_CODE)
                    field(KEY_OPTION_TYPE)
                    field(KEY_DESCRIPTION)
                    field(KEY_POSITION)
                    field(KEY_URL)
                    field(KEY_ACTIVE)
                    fieldObject(KEY_SUB_OPTION) {
                        field(KEY_OPTION_CODE)
                        field(KEY_OPTION_TYPE)
                        field(KEY_DESCRIPTION)
                        field(KEY_POSITION)
                        field(KEY_URL)
                        field(KEY_ACTIVE)
                    }
                }
            }
        }
    }

    private fun Kraph.FieldBuilder.getInput(): KraphVariable {
        val jsonValue = Params(country, role, region, zone, section, application).toJson()
        return variable(VAR_KEY_PARAMS, VAR_TYPE_PARAMS, jsonValue)
    }

    @Serializable
    private data class Params(
        @SerialName(KEY_COUNTRY)
        val country: String,
        @SerialName(KEY_ROLE)
        val role: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_APP)
        val application: String
    ) {

        fun toJson(): String {
            return JsonEncodedDefault.encodeToString(serializer(), this)
        }

    }

    @Serializable
    data class Data(
        /** CAMBIOS AJ*/
        @SerialName(KEY_OPTIONS)
        val options: MutableList<Option>
    ) {

        @Serializable
        data class Option(
            @SerialName(KEY_OPTION_CODE)
            val code: Int,
            @SerialName(KEY_OPTION_TYPE)
            val type: String,
            @SerialName(KEY_DESCRIPTION)
            val description: String,
            @SerialName(KEY_POSITION)
            val position: Int,
            @SerialName(KEY_URL)
            val url: String,
            @SerialName(KEY_ACTIVE)
            val active: Boolean,
            @SerialName(KEY_SUB_OPTION)
            val subOptions: List<SubOption>
        ) {

            @Serializable
            data class SubOption(
                @SerialName(KEY_OPTION_CODE)
                val code: Int,
                @SerialName(KEY_OPTION_TYPE)
                val type: String,
                @SerialName(KEY_DESCRIPTION)
                val description: String,
                @SerialName(KEY_POSITION)
                val position: Int,
                @SerialName(KEY_URL)
                val url: String,
                @SerialName(KEY_ACTIVE)
                val active: Boolean
            )

        }

    }

}
