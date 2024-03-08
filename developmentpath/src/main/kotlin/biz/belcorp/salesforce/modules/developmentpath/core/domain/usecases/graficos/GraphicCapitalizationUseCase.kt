package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.CapitalizationSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class GraphicCapitalizationUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val graphicsSERepository: GraphicsSERepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGraphicCapitalization(personIdentifier: PersonIdentifier): List<CapitalizationSe> {
        val person = businessPartnerRepository.getBusinessPartner(personIdentifier.code)
        val campaigns = getCampaigns()
        val capitalizationList = graphicsSERepository.getCapitalization(person.uaKey, campaigns)
        return createAllCapitalization(capitalizationList, campaigns)
    }

    private fun createAllCapitalization(
        capitalizationList: List<CapitalizationSe>,
        campaigns: List<String>
    ): List<CapitalizationSe> {
        return campaigns.map { campaign ->
            capitalizationList.firstOrNull { it.campaign == campaign }
                ?: CapitalizationSe(campaign = campaign)
        }.sortedBy { it.campaign }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .take(Constant.NUMBER_SIX)
    }
}
