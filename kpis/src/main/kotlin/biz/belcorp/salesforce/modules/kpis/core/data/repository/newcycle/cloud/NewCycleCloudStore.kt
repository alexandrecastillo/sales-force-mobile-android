package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto.NewCycleQuery

class NewCycleCloudStore(
    private val newCycleApi: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getNewCycleIndicator(query: NewCycleQuery): NewCycleQuery.Data {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            newCycleApi.getKpiNewCycle(request)
        }
        return requireNotNull(response?.data)
    }

}
