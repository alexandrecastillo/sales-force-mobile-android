package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ProfitSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class GraphicProfitSeUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val graphicsSERepository: GraphicsSERepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGraphicProfit(personIdentifier: PersonIdentifier): List<ProfitSe> {
        val person = businessPartnerRepository.getBusinessPartner(personIdentifier.code)
        val campaigns = getCampaigns()
        val profitList = graphicsSERepository.getProfitSe(person.uaKey, campaigns)
        return createAllProfit(profitList, campaigns)
    }

    private fun createAllProfit(
        profitList: List<ProfitSe>,
        campaigns: List<String>
    ): List<ProfitSe> {
        return campaigns.map { campaign ->
            profitList.firstOrNull { it.campaign == campaign }
                ?: ProfitSe(campaign = campaign)
        }.sortedBy { it.campaign }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .take(Constant.NUMBER_SIX)
    }
}
