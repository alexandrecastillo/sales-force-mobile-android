package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync.SyncRepository

class GetCampaignProjectionInfoUseCase(
    private val campaignProjectionRepository: SyncRepository,
    private val sessionManager: SessionRepository
) {
    val session by lazy { requireNotNull(sessionManager.getSession()) }

    suspend fun getCampaignProjectionInfo(sectionPartner: String?) =
        campaignProjectionRepository.getSavedProjectedCampaign(session.llaveUA, sectionPartner)
}
