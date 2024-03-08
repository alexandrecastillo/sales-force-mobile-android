package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.utils.isNull
import biz.belcorp.salesforce.modules.calculator.core.data.network.CampaignProjectionApi
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto.CampaignProjectionDto
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.getprojection.CampaignProjectionConsultantQuery
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.saveprojection.SaveCampaignProjectionMutation

class CampaignProjectionCloudDataStore(
    private val campaignProjectionInfoApi: CampaignProjectionApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getCampaignProjectionInfo(query: CampaignProjectionConsultantQuery):
        CampaignProjectionDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            campaignProjectionInfoApi.getCampaignProjectionInfo(request)
        }
        return requireNotNull(response).data
    }

    suspend fun saveCampaignProjectionInfo(query: SaveCampaignProjectionMutation):
        CampaignProjectionDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            campaignProjectionInfoApi.saveCampaignProjectionInfo(request)
        }
        return requireNotNull(response).data
    }
}
