package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class TransactionAccountQuery(private val request: TransactionAccountParams) : BaseQuery() {

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
                field(KEY_CONSULTANT_ID)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_TRANSACTION_DATE)
                field(KEY_TRANSACTION_DESCRIPTION)
                field(KEY_TRANSACTION_TYPE)
                field(KEY_AMOUNT)
            }
        }
    }

}
