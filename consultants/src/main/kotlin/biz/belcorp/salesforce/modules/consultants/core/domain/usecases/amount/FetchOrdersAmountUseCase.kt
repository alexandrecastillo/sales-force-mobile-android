package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.amount.OrdersAmountRepository


class FetchOrdersAmountUseCase(
    private val amountsRepository: OrdersAmountRepository,
    private val sessionRepository: SessionRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun fetch(uaKey: LlaveUA) {
        amountsRepository.fetch(session.countryIso, uaKey, session.campaign.codigo)
    }

}
