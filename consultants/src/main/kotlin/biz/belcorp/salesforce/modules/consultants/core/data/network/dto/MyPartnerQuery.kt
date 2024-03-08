package biz.belcorp.salesforce.modules.consultants.core.data.network.dto

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph


class MyPartnerQuery(val params: MyPartnersParams) : BaseQuery() {

    override val keyFunctionName = "NewGetBusinessPartner"
    override val keyFilter = "input"
    override val keyFilterType = "ParamsBP!"
    override val keyCollection = "NewBusinessPartner"
    override val keyInput = "input"


    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_CONSULTANT_ID)
                field(KEY_CONSULTANT_CODE)
                field(KEY_ANNIVERSARY_DATE)
                field(KEY_ADMISSION_CAMPAIGN)
                fieldObject(KEY_PERSONAL_INFO) {
                    field(KEY_FULL_NAME)
                    field(KEY_FIRST_NAME)
                    field(KEY_SURE_NAME)
                    field(KEY_SECOND_NAME)
                    field(KEY_SECOND_SURE_NAME)
                    field(KEY_DOCUMENT_NUMBER)
                    field(KEY_EMAIL)
                    field(KEY_ADDRESS)
                    field(KEY_CELL_PHONE_NUMBER)
                    field(KEY_TELEPHONE_NUMBER)
                    field(KEY_BIRTHDAY)
                }
                fieldObject(KEY_LEVEL_CODE) {
                    field(KEY_NAME_CODE)
                    field(KEY_CODE_CODE)
                }
                fieldObject(KEY_NEXT_LEVEL_CODE) {
                    fieldObject(KEY_SALES_CODE) {
                        field(KEY_REQUIREMENT_CODE)
                        field(KEY_REAL_CODE)
                        field(KEY_ACCOMPLISHED_CODE)
                    }
                    fieldObject(KEY_ORDERS_CODE) {
                        field(KEY_REQUIREMENT_CODE)
                        field(KEY_REAL_CODE)
                        field(KEY_ACCOMPLISHED_CODE)
                    }
                    field(KEY_NAME_CODE)
                    field(KEY_ACCOMPLISHED_CODE)
                    field(KEY_CAMPAIGNS_ACCOMPLISHED_CODE)
                }
                field(KEY_PENDING_DEBT)
                field(KEY_PRODUCTIVITY)
                fieldObject(KEY_BILLING_INFO) {
                    field(KEY_FIRST_CAMPAIGN)
                    field(KEY_LAST_CAMPAIGN)
                }
                field(KEY_IS_SUCCESSFUL)
                fieldObject(KEY_IS_SUCCESSFUL_HISTORY) {
                    field(KEY_CAMPAIGN)
                    field(KEY_VALUE)
                }


            }
        }
    }
}
