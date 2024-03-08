package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.*

data class DigitalSaleCoQuery(val params: DigitalSaleCoRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_NAME_CO_KEY
    override val keyFilter = FILTER_CO_KEY
    override val keyFilterType = FILTER_TYPE_CO_KEY
    override val keyCollection = COLLECTION_CO_KEY
    override val keyInput = INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(CAMPAIGN_KEY)
                field(REGION_KEY)
                field(ZONE_KEY)
                field(SECTION_KEY)
                field(CONSULTANT_CODE_KEY)
                fieldObject(GANAMAS_KEY) {
                    field(IS_SUBSCRIBED_KEY)
                    field(CAMPAIGN_SUBSCRIPTION_KEY)
                    field(BUY_KEY)
                }
                fieldObject(USABILITY_KEY) {
                    field(DIGITAL_CATALOG_KEY)
                    field(MAKEUP_APP_KEY)
                }
                fieldObject(ONLINE_STORE_KEY) {
                    field(BUY_KEY)
                    field(SHARE_KEY)
                    field(IS_SUBSCRIBED_KEY)
                    field(CAMPAIGN_SUBSCRIPTION_KEY)
                    field(MTO_SALES_KEY)
                }
                fieldObject(DIGITAL_CATALOG_KEY) {
                    field(SHARED_CATALOGS_KEY)
                    field(RECEIVE_ORDERS_KEY)
                    field(APPROVE_ORDERS_KEY)
                    field(APPROVE_PRE_ORDERS_KEY)
                    field(RECEIVE_PRE_ORDERS_KEY)
                    field(DIGITAL_CATALOG_QUANTITY_SHARE_KEY)
                }
                field(BUY_DIGITAL_OFFERS_KEY)
                field(OPEN_VIRTUAL_COACH_KEY)
                field(ORDER_SENT_APP_KEY)
                field(UNIQUE_IP_KEY)
            }
        }
    }
}
