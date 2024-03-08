package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ActivesRetention
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class ActivesRetentionUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val graphicsSERepository: GraphicsSERepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGraphicActives(personIdentifier: PersonIdentifier): List<ActivesRetention> {
        val person = businessPartnerRepository.getBusinessPartner(personIdentifier.code)
        val campaigns = getCampaigns()
        val netSales = graphicsSERepository.getActivesRetention(person.uaKey, campaigns)
        return createAllActives(netSales, campaigns)
    }

    private fun createAllActives(
        actives: List<ActivesRetention>,
        campaigns: List<String>
    ): List<ActivesRetention> {
        return campaigns.map { campaign ->
            actives.firstOrNull { it.campaign == campaign } ?: ActivesRetention(campaign = campaign)
        }.sortedBy { it.campaign }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .take(Constant.NUMBER_SIX)
    }
}
