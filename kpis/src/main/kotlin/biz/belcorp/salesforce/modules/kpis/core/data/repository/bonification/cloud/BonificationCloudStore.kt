package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.kpis.core.data.network.BelcorpApi
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusQuery

class BonificationCloudStore(
    private val api: BelcorpApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getBonificationInfo(query: BonusQuery): BonusDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getBonificationInfo(request)
        }
        return requireNotNull(response?.data)
    }

}
