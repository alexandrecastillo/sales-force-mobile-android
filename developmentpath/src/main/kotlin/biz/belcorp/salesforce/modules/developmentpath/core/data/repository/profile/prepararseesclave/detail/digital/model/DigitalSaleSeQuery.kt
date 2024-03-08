package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.COLLECTION_SE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.FILTER_SE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.FILTER_TYPE_SE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.FUNCTION_NAME_SE_KEY
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.INPUT_KEY

data class DigitalSaleSeQuery(val params: DigitalSaleSeRequestParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_NAME_SE_KEY
    override val keyFilter = FILTER_SE_KEY
    override val keyFilterType = FILTER_TYPE_SE_KEY
    override val keyCollection = COLLECTION_SE_KEY
    override val keyInput = INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(CAMPAIGN_KEY)
                field(REGION_KEY)
                field(ZONE_KEY)
                field(SECTION_KEY)
                fieldObject(GANAMAS_KEY) {
                    field(SUBSCRIBED_KEY)
                    field(SUBSCRIBED_BUYERS_KEY)
                    field(SUBSCRIBED_NOT_BUYERS_KEY)
                    field(NOT_SUBSCRIBED_BUYERS_KEY)
                    field(NOT_SUBSCRIBED_NOT_BUYERS_KEY)
                }
                fieldObject(DIGITAL_CATALOG_KEY) {
                    field(SHARE_CATALOG_KEY)
                    field(RECEIVE_ORDERS_KEY)
                    field(APPROVE_ORDERS_KEY)
                }
                fieldObject(VIRTUAL_COACH_KEY) {
                    field(OPEN_KEY)
                    field(RECEIVE_KEY)
                    field(CLICK_KEY)
                }
                fieldObject(MAKEUP_APP_KEY) {
                    field(USABILITY_KEY)
                }
                fieldObject(ESIKA_AHORA_KEY) {
                    field(SUBSCRIBED_KEY)
                }
                field(UNIQUE_IP_PERCENTAGE_KEY)
                field(FINAL_ACTIVE_CONSULTANTS_KEY)
                field(ORDER_SENT_APP_KEY)
                field(MULTI_BRAND_KEY)
            }
        }
    }
}
