package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.deletedream.DreamDeleteQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream.DreamCreateDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream.DreamCreateQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.dto.DreamConsultantDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.getdreams.DreamConsultantQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream.DreamEditDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream.DreamEditQuery

class DreamCloudDataStore(
    private val profileInfoApi: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {
    suspend fun syncConsultantDreams(query: DreamConsultantQuery): DreamConsultantDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.getConsultantDreams(request)
        }
        return requireNotNull(response).data
    }

    suspend fun deleteDream(query: DreamDeleteQuery) {
        val request = query.get().toRequestString()
        apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.deleteDream(request)
        }
    }


    suspend fun syncSaveConsultantDreams(query: DreamCreateQuery): DreamCreateDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.saveConsultantDreams(request)
        }
        return requireNotNull(response).data
    }

    suspend fun syncEditConsultantDreams(query: DreamEditQuery): DreamEditDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            profileInfoApi.saveEditDreams(request)
        }
        return requireNotNull(response).data
    }

}
