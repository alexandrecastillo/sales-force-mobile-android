package biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity

interface SyncRepository {

    suspend fun sync(uaKey: LlaveUA, country: String, campaign: String, phase: String, sectionPartner: String? = null)
    suspend fun getSavedProjectedCampaign(uaKey: LlaveUA, sectionPartner: String?): CampaignProjectionEntity?
    suspend fun saveProjectedCampaign(
        uaKey: LlaveUA,
        country: String,
        campaign: String,
        campaignProjectionInfoEntity: CampaignProjectionEntity,
        sectionPartner: String?
    )

}
