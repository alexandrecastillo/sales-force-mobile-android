package biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

class BusinessPartnerQuery(private val params: Params) : BaseQuery() {

    override val keyFunctionName = OPERATION_NAME
    override val keyCollection = KEY_BUSINESS_PARTNERS
    override val keyFilter = VAR_KEY_PARAMS
    override val keyFilterType = VAR_TYPE_PARAMS
    override val keyInput = ARG_INPUT

    override fun getJson() = params.toJson()

    override fun get(): Kraph {
        return Kraph {
            query(keyFunctionName) {
                fieldObject(
                    keyCollection,
                    mapOf(keyInput to getInput())
                ) {
                    field(KEY_REGION)
                    field(KEY_ZONE)
                    field(KEY_SECTION)
                    field(KEY_ID)
                    field(KEY_CODE)
                    field(KEY_ANNIVERSARY_DATE)
                    field(KEY_CAMPAIGN_ADMISSION)
                    fieldObject(KEY_PERSONAL_INFO) {
                        field(KEY_DOCUMENT)
                        field(KEY_FULL_NAME)
                        field(KEY_FIRST_NAME)
                        field(KEY_SECOND_NAME)
                        field(KEY_SURNAME)
                        field(KEY_SECOND_SURNAME)
                        field(KEY_EMAIL)
                        field(KEY_ADDRESS)
                        field(KEY_CELLPHONE)
                        field(KEY_HOMEPHONE)
                        field(KEY_BIRTH_DATE)
                    }
                    fieldObject(KEY_LEVEL) {
                        field(KEY_LEVEL_CODE)
                        field(KEY_LEVEL_NAME)
                    }
                    field(KEY_PENDING_DEBT)
                    field(KEY_PRODUCTIVITY)
                    fieldObject(KEY_BILLING_INFO) {
                        field(KEY_BILLING_FIRST_CAMPAIGN)
                        field(KEY_BILLING_LAST_CAMPAIGN)
                    }
                    field(KEY_SUCCESSFUL)
                    fieldObject(KEY_SUCCESSFUL_HISTORIC) {
                        field(KEY_CAMPAIGN)
                        field(KEY_SUCCESSFUL_VALUE)
                    }
                }
            }
        }
    }

    @Serializable
    data class Params(
        @SerialName(KEY_COUNTRY)
        val country: String = Constant.EMPTY_STRING,
        @SerialName(KEY_CAMPAIGN)
        val campaign: String = Constant.EMPTY_STRING,
        @SerialName(KEY_REGION)
        val region: String = Constant.EMPTY_STRING,
        @SerialName(KEY_ZONE)
        val zone: String = Constant.EMPTY_STRING,
        @SerialName(KEY_SECTION)
        val section: String = Constant.EMPTY_STRING
    ) {
        fun toJson(): String {
            return JsonEncodedDefault.encodeToString(serializer(), this)
        }
    }

}
