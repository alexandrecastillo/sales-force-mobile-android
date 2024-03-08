package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.deletebpdream.DreamDeleteBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate.DreamCreateBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate.DreamCreateBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream.DreamEditBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream.DreamEditBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpQuery

class DreamBpCloudDataStore(
    private val profileInfoApi: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun syncBpDreams(query: DreamBpQuery): DreamBpDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.getBpDreams(request)
        }
        return requireNotNull(response).data
    }

    suspend fun syncSaveBusinessPartnerDreams(query: DreamCreateBpQuery): DreamCreateBpDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.saveBusinessPartnerDream(request)
        }
        return requireNotNull(response).data
    }

    suspend fun deleteBusinessPartnerDream(query: DreamDeleteBpQuery) {
        val request = query.get().toRequestString()
        apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.deleteBusinessPartnerDream(request)
        }
    }

    suspend fun syncEditBusinessPartnerDreams(query: DreamEditBpQuery): DreamEditBpDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.saveEditBusinessPartnerDreams(request)
        }
        return requireNotNull(response).data
    }
}
