package biz.belcorp.salesforce.modules.kpis.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull

class KpiGridSelectorUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignRepository: CampaniasRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    fun getRoleAndCampaign(): Pair<Rol, Campania?> {
        val campaign = getCurrentCampaign(session.llaveUA)
        return Pair(session.rol, campaign)
    }

    private fun getCurrentCampaign(uaKey: LlaveUA): Campania? {
        val campaign =
            requireNotNull(campaignRepository.obtenerCampaniaActual(uaKey.formatHyphenIfNull()))
        val isBilling = CampaignRules.isBilling(campaign)
        return if (isBilling) campaign else null
    }
}
