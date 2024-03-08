package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.dto.RankingQuery

class ProfileRankingCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun syncRankingInfo(query: RankingQuery): RankingDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getProfileRanking(request)
        }
        return requireNotNull(response?.data)
    }

}
