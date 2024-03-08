package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class ConsultantsQuery(private val request: ConsultantsParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                fieldObject(KEY_LIST) {
                    field(KEY_ID)
                    field(KEY_CAMPAIGN)
                    field(KEY_REGION)
                    field(KEY_ZONE)
                    field(KEY_SECTION)
                    field(KEY_CONSULTANT_ID)
                    field(KEY_CODE)
                    field(KEY_CHECK_DIGIT)
                    field(KEY_MULTIBRAND)
                    fieldObject(KEY_PERSONAL_INFO) {
                        field(KEY_FULL_NAME)
                        field(KEY_FIRST_NAME)
                        field(KEY_SURNAME)
                        field(KEY_SECOND_NAME)
                        field(KEY_SECOND_SURNAME)
                        field(KEY_DOCUMENT_NUMBER)
                        field(KEY_ADDRESS)
                        field(KEY_ADDRESS_REFERENCE)
                        field(KEY_PHONE)
                        field(KEY_EMAIL)
                        field(KEY_BIRTHDAY)
                        field(KEY_CHECK_DIGIT)
                    }
                    field(KEY_ANNIVERSARY_DATE)
                    field(KEY_CAMPAIGN_ADMISSION)
                    fieldObject(KEY_CONSTANCY) {
                        field(KEY_CONSTANCY_U6C)
                        field(KEY_CONSTANCY_U18C)
                        field(KEY_CONSTANCY_NEW)
                        field(KEY_CONSTANCY_ESTABLISHED)
                    }
                    fieldObject(KEY_SEGMENT) {
                        field(KEY_SEGMENT_CODE)
                        field(KEY_SEGMENT_DESCRIPTION)
                    }
                    fieldObject(KEY_BRIGHT_SEGMENT) {
                        field(KEY_BRIGHT_SEGMENT_CODE)
                        field(KEY_BRIGHT_SEGMENT_DESCRIPTION)
                    }
                    fieldObject(KEY_BRIGHT_PATH) {
                        field(KEY_BRIGHT_PATH_PERIOD)
                        field(KEY_BRIGHT_PATH_SALE)
                        field(KEY_BRIGHT_PATH_LEVEL_DESCRIPTION)
                        field(KEY_BRIGHT_PATH_LEVEL_CODE)
                        field(KEY_BRIGHT_PATH_CONSTANCY)
                    }
                    fieldObject(KEY_STATE) {
                        field(KEY_STATE_CODE)
                        field(KEY_STATE_DESCRIPTION)
                    }
                    field(KEY_IS_PEG)
                    field(KEY_IS_NEW)
                    field(KEY_IS_NEW_INCONSTANT)
                    field(KEY_IS_AUTHORIZED)
                    field(KEY_IS_POTENTIAL_ENTRY)
                    field(KEY_IS_POTENTIAL_REENTRY)
                    fieldObject(KEY_BILLING_INFO) {
                        field(KEY_FIRST_CAMPAIGN)
                        field(KEY_LAST_CAMPAIGN)
                        field(KEY_ORDER_STATUS)
                        field(KEY_BILLING_AMOUNT)
                        field(KEY_ORDER_MTO_AMOUNT)
                    }
                    fieldObject(KEY_SALES_INFO) {
                        field(KEY_CATALOG_SALE)
                        field(KEY_ORDER_RANGE)
                        field(KEY_IS_ORDER_SENT)
                    }
                    fieldObject(KEY_COLLECTION_INFO) {
                        field(KEY_PENDING_DEBT)
                        field(KEY_COLLECTION_PERCENTAGE)
                    }
                    fieldObject(KEY_GEO_INFO) {
                        field(KEY_LATITUDE)
                        field(KEY_LONGITUDE)
                    }
                    field(KEY_REENTRIES_U18C)
                    fieldObject(KEY_DIGITAL) {
                        field(KEY_SHARE_CATALOG)
                        field(KEY_SUGGESTED_MESSAGE)
                        field(KEY_DIGITAL_SEGMENT)
                    }
                    field(KEY_CASH_PAYMENT)
                    field(KEY_LAST_MODIFIED)
                }
                field(KEY_TOTAL_ROWS)
                field(KEY_TOTAL_PAGES)
                field(KEY_PAGE)
            }
        }
    }
}
