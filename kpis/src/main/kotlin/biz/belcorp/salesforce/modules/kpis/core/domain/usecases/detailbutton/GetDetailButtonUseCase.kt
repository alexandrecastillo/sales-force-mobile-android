package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.detailbutton

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR

class GetDetailButtonUseCase(
    private val campaignRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun isBillingForDetailButton(): Pair<String, Boolean> {
        val useFirstDayFlag = session.rol.isDV() || session.rol.isGR()
        val currentCampaign = campaignRepository.obtenerCampaniaActualSuspend(session.llaveUA)
        val previousCampaign = campaignRepository.getPreviousCampaignSuspend(session.llaveUA)
        val isBilling = CampaignRules.isBilling(
            campaign = currentCampaign,
            useFirstDayFlag = useFirstDayFlag
        )
        return Pair(previousCampaign.codigo, isBilling)
    }
}
