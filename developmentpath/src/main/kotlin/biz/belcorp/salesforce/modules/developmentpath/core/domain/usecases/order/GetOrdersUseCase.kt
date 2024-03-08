package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.order

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.OrderIndicatorRDD
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.order.OrderIndicatorRDDRepository

class GetOrdersUseCase(
    private val repository: OrderIndicatorRDDRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository
) {

    private val campaign by lazy { getCurrentCampaign() }
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    private fun getCurrentCampaign(): Campania {
        return requireNotNull(campaignsRepository.obtenerCampaniaActual(session.llaveUA))
    }

    suspend fun getOrderIndicator(): OrderIndicatorRDD {
        return repository.getOrderIndicator(session.llaveUA, campaign.codigo)
    }
}
