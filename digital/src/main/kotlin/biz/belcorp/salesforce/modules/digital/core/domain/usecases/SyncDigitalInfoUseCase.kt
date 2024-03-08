package biz.belcorp.salesforce.modules.digital.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.digital.core.domain.repository.DigitalRepository

class SyncDigitalInfoUseCase(
    private val sessionRepository: SessionRepository,
    private val digitalRepository: DigitalRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun sync() {
        with(session) {
            digitalRepository.sync(countryIso, campaign.codigo, llaveUA)
        }
    }

}
