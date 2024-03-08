package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync.SyncRepository

class SyncPartnerUseCase(
    private val syncRepository: SyncRepository,
    private val sesionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sesionRepository.getSession()) }

    suspend fun sync(section: String) {
        val ua = session.llaveUA

        return syncRepository.sync(
            ua,
            country = session.countryIso,
            session.campaign.codigo,
            session.campaign.getPhase(),
            section
        )
    }

}
