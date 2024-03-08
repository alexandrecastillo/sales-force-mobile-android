package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_SIX
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.NetSaleSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class GraphicNetSaleSeUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val graphicsSERepository: GraphicsSERepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGraphicNetSaleSe(personIdentifier: PersonIdentifier): List<NetSaleSe> {
        val person = businessPartnerRepository.getBusinessPartner(personIdentifier.code)
        val campaigns = getCampaigns()
        val netSales = graphicsSERepository.getNetSaleSe(person.uaKey, campaigns)
        return createAllNetSalesSe(netSales, campaigns)
    }

    private fun createAllNetSalesSe(
        netSales: List<NetSaleSe>,
        campaigns: List<String>
    ): List<NetSaleSe> {
        return campaigns.map { campaign ->
            netSales.firstOrNull { it.campaign == campaign } ?: NetSaleSe(campaign = campaign)
        }.sortedBy { it.campaign }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .take(NUMBER_SIX)
    }
}
