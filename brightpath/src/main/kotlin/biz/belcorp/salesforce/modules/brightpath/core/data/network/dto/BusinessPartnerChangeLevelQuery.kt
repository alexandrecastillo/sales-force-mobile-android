package biz.belcorp.salesforce.modules.brightpath.core.data.network.dto

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class BusinessPartnerChangeLevelQuery(val params: BusinessPartnerChangeLevelParams) : BaseQuery() {

    override val keyFunctionName = COLLECTION_BUSINESS_PARTNER_CHANGE_LEVEL_KEY
    override val keyFilter = FILTER_BUSINESS_PARTNER_CHANGE_LEVEL_KEY
    override val keyFilterType = FILTER_BUSINESS_PARTNER_CHANGE_LEVEL_TYPE_KEY
    override val keyCollection = COLLECTION_BUSINESS_PARTNER_CHANGE_LEVEL_KEY
    override val keyInput = INPUT_KEY


    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_CONSULTANT_CODE)
                fieldObject(KEY_LEVEL_CODE) {
                    field(KEY_CODE_CODE)
                    field(KEY_NAME_CODE)
                    field(KEY_CONSECUTIVE_CAMPAIGNS_CODE)
                    field(KEY_CAMPAIGNS_NOT_ACCOMPLISHED_CODE)
                    field(KEY_ACCOMPLISHED_CODE)
                }
                fieldObject(KEY_NEXT_LEVEL_CODE) {
                    field(KEY_NAME_CODE)
                    field(KEY_ACCOMPLISHED_CODE)
                    field(KEY_CAMPAIGNS_ACCOMPLISHED_CODE)
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
                }
                fieldObject(KEY_LEVEL_REQUIREMENT_CODE) {
                    field(KEY_LEVEL_CODE)
                    field(KEY_MINIMUM_SALES_CODE)
                    field(KEY_MINIMUM_ORDERS_CODE)
                }
                field(KEY_CAMPAIGN_REQUIREMENT_CODE)


                field(KEY_SECTION_SALES_CODE)
                field(KEY_SECTION_ORDERS_CODE)
                field(KEY_GAIN_AMOUNT_LOW_VALUE_CODE)
                field(KEY_GAIN_AMOUNT_LOW_VALUE_PLUS_CODE)
                field(KEY_GAIN_AMOUNT_HIGH_VALUE_CODE)
                field(KEY_GAIN_AMOUNT_HIGH_VALUE_PLUS_CODE)
            }
        }
    }
}
