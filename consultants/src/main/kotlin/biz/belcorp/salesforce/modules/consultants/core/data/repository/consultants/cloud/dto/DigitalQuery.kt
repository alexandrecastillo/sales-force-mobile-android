package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class DigitalQuery(private val params: DigitalParams) : BaseQuery() {

    override val keyFunctionName = DIGITAL_FUNCTION_KEY
    override val keyCollection = DIGITAL_COLLECTION_KEY
    override val keyFilter = DIGITAL_FILTER_KEY
    override val keyFilterType = DIGITAL_FILTER_TYPE
    override val keyInput = DIGITAL_INPUT_KEY

    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_CONSULTANT_CODE)
                field(KEY_IS_SUBSCRIBED)
                field(KEY_SHARE)
                field(KEY_BUY)
                field(KEY_SALE)
                field(KEY_ORDERS)
            }
        }
    }
}
