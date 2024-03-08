package biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class DigitalQuery(val params: DigitalParams) : BaseQuery() {

    override val keyFunctionName =
        DIGITAL_FUNCTION_NAME_KEY
    override val keyFilter =
        DIGITAL_FILTER_KEY
    override val keyFilterType =
        DIGITAL_FILTER_TYPE_KEY
    override val keyCollection =
        DIGITAL_COLLECTION_KEY
    override val keyInput =
        DIGITAL_INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(DIGITAL_CAMPAIGN_KEY)
                field(DIGITAL_REGION_KEY)
                field(DIGITAL_ZONE_KEY)
                field(DIGITAL_SECTION_KEY)
                field(DIGITAL_PROFILE_KEY)
                field(DIGITAL_ACTIVES_KEY)
                field(DIGITAL_SUBSCRIBED_KEY)
                field(DIGITAL_SHARE_KEY)
                field(DIGITAL_BUY_KEY)
                field(DIGITAL_SUBSCRIBED_ACTIVES_RATIO_KEY)
                field(DIGITAL_SHARE_ACTIVES_RATIO_KEY)
                field(DIGITAL_SHARE_SUBSCRIBED_RATIO_KEY)
                field(DIGITAL_BUY_ACTIVES_RATIO_KEY)
                field(DIGITAL_BUY_SUBSCRIBED_RATIO_KEY)
            }
        }
    }

}
