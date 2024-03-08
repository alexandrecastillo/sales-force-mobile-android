package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.*

class TopSalesSeQuery(private val request: TopSalesSeParams) : BaseQuery() {
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
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                fieldObject(KEY_BRANDS) {
                    field(KEY_NAME)
                    field(KEY_UNITS)
                }
                fieldObject(KEY_TOP_PRODUCTS) {
                    field(KEY_TOP)
                    field(KEY_NAME)
                    field(KEY_AVERAGE_UNITS)
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_UNITS)
                    }
                }
            }
        }
    }
}
