package biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto

import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.core.utils.kraph.types.KraphVariable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

data class ConfigurationQuery(val country: String) {

    companion object {
        private const val OPERATION_NAME = "configuration"

        private const val KEY_CONFIGURATION = "Configuration"
        private const val KEY_COUNTRY = "country"

        private const val ARG_INPUT = "input"

        private const val VAR_KEY_PARAMS = "Country"
        private const val VAR_TYPE_PARAMS = "Country"

        private const val KEY_ZONE_PDV = "zonePdV"
        private const val KEY_FLAG_PDV = "flag_pdv"
        private const val KEY_CURRENCY_SYMBOL = "currency_symbol"
        private const val KEY_PHONE_CODE = "phone_code"
        private const val KEY_COUNTRY_NAME = "name"
        private const val KEY_MINIMAL_ORDER_AMOUNT = "minimal_order_amount"
        private const val KEY_TIPPING_POINT = "tipping_point"
        private const val KEY_FLAG_SHOW_PROACTIVE = "flag_show_proactive"
        private const val KEY_CODE_REGION = "code_region"
        private const val KEY_CODE_ZONE = "code_zone"
        private const val KEY_FLAG_DATA_REPORT = "flag_datareport"
        private const val KEY_FLAG_GANAMAS = "flag_ganamas"
        private const val KEY_MAIN_BRAND = "main_brand"
        private const val KEY_FLAG_MTO = "flag_mto"
    }


    fun get(): Kraph {
        return Kraph {
            query(OPERATION_NAME) {
                fieldObject(
                    KEY_CONFIGURATION,
                    mapOf(ARG_INPUT to getInput())
                ) {
                    field(KEY_COUNTRY)
                    field(KEY_FLAG_PDV)
                    field(KEY_CURRENCY_SYMBOL)
                    field(KEY_PHONE_CODE)
                    field(KEY_COUNTRY_NAME)
                    field(KEY_MINIMAL_ORDER_AMOUNT)
                    field(KEY_TIPPING_POINT)
                    field(KEY_FLAG_SHOW_PROACTIVE)
                    field(KEY_FLAG_GANAMAS)
                    field(KEY_MAIN_BRAND)
                    field(KEY_FLAG_MTO)
                    fieldObject(KEY_ZONE_PDV) {
                        field(KEY_CODE_REGION)
                        field(KEY_CODE_ZONE)
                        field(KEY_FLAG_PDV)
                        field(KEY_FLAG_DATA_REPORT)
                    }
                }
            }
        }
    }

    private fun Kraph.FieldBuilder.getInput(): KraphVariable {
        val jsonValue = Params(
            country
        ).toJson()
        return variable(
            VAR_KEY_PARAMS,
            VAR_TYPE_PARAMS, jsonValue
        )
    }

    @Serializable
    data class Params(
        @SerialName(KEY_COUNTRY)
        val country: String
    ) {
        fun toJson(): String {
            return JsonEncodedDefault.encodeToString(serializer(), this)
        }
    }

    @Serializable
    data class Data(
        @SerialName(KEY_CONFIGURATION)
        val configuration: Configuration
    ) {

        @Serializable
        data class Configuration(
            @SerialName(KEY_COUNTRY)
            val country: String,
            @SerialName(KEY_COUNTRY_NAME)
            val countryName: String,
            @SerialName(KEY_PHONE_CODE)
            val phoneCode: String,
            @SerialName(KEY_CURRENCY_SYMBOL)
            val currencySymbol: String,
            @SerialName(KEY_MINIMAL_ORDER_AMOUNT)
            val minimalOrderAmount: Long,
            @SerialName(KEY_TIPPING_POINT)
            val tippingPoint: Long,
            @SerialName(KEY_FLAG_SHOW_PROACTIVE)
            val flagShowProactive: Boolean,
            @SerialName(KEY_FLAG_PDV)
            val isPdv: Boolean,
            @SerialName(KEY_FLAG_GANAMAS)
            val isGanaMas: Boolean,
            @SerialName(KEY_MAIN_BRAND)
            val mainBrand: String,
            @SerialName(KEY_FLAG_MTO)
            val flagMto: Int,
            @SerialName(KEY_ZONE_PDV)
            val zonesPdv: List<ZonePdv>

        ) {

            @Serializable
            data class ZonePdv(
                @SerialName(KEY_CODE_REGION)
                val regionCode: String,
                @SerialName(KEY_CODE_ZONE)
                val zoneCode: String,
                @SerialName(KEY_FLAG_PDV)
                val isPdv: Boolean,
                @SerialName(KEY_FLAG_DATA_REPORT)
                val isDataReport: Boolean
            )
        }
    }
}
