package biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.RejectedOrdersParams
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository

class RejectedOrdersSyncUseCase(
    private val rejectedOrdersRepository: RejectedOrdersRepository,
    private val sessionRepository: SessionRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun sync() = with(session) {
        rejectedOrdersRepository.sync(
            RejectedOrdersParams(countryIso, llaveUA, campaign.nombreCorto)
        )
    }

}
