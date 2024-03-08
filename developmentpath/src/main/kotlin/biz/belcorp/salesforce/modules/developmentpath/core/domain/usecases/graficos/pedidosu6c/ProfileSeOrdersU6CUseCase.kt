package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.pedidosu6c

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c.OrderU6C
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class ProfileSeOrdersU6CUseCase(
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val graphicsSERepository: GraphicsSERepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGraphicOrdersSe(personIdentifier: PersonIdentifier): List<OrderU6C> {
        val person = businessPartnerRepository.getBusinessPartner(personIdentifier.code)
        val campaigns = getCampaigns()
        val orders = graphicsSERepository.getOrdersSe(person.uaKey, campaigns)
        return createAllOrdersSe(orders, campaigns)
    }

    private fun createAllOrdersSe(
        orders: List<OrderU6C>,
        campaigns: List<String>
    ): List<OrderU6C> {
        return campaigns.map { campaign ->
            orders.firstOrNull { it.campaign == campaign } ?: OrderU6C(campaign = campaign)
        }.sortedBy { it.campaign }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
            .map { it.codigo }
            .take(Constant.NUMBER_SIX)
    }

}
