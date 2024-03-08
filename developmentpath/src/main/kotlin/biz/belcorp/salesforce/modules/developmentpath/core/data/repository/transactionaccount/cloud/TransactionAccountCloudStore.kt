package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountQuery

class TransactionAccountCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getTransactionAccount(query: TransactionAccountQuery): TransactionAccountDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getTransactionAccount(request)
        }
        return requireNotNull(response?.data)
    }

}
