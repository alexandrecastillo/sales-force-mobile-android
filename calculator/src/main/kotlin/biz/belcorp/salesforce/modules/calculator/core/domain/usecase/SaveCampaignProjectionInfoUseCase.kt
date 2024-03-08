package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync.SyncRepository

class SaveCampaignProjectionInfoUseCase(
    private val campaignProjectionRepository: SyncRepository,
    private val sessionManager: SessionRepository
) {
    private val session by lazy { requireNotNull(sessionManager.getSession()) }


    suspend fun saveCampaignInfo(campaignProjectionEntity: CampaignProjectionEntity, sectionPartner: String?) =
        campaignProjectionRepository.saveProjectedCampaign(
            session.llaveUA, session.countryIso,
            session.campaign.codigo,
            campaignProjectionEntity,
            sectionPartner
        )
}
