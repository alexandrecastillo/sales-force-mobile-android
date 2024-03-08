package biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.RejectedOrdersParams
import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository

class RejectedOrdersUseCase(
    private val rejectedOrdersRepository: RejectedOrdersRepository,
    private val sessionRepository: SessionRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }
    private val campaignCode get() = session.campaign.codigo

    suspend fun getRejectedOrders(uaKey: LlaveUA): List<RejectedOrdersBilling> {
        syncRejectedOrders()
        return rejectedOrdersRepository.getRejectedOrders(uaKey, campaignCode)
    }

    private suspend fun syncRejectedOrders() = with(session) {
        rejectedOrdersRepository.sync(
            RejectedOrdersParams(
                countryIso,
                llaveUA,
                campaignCode,
                forceSync = false
            )
        )
    }

}
