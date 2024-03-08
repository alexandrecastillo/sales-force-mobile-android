package biz.belcorp.salesforce.modules.brightpath.core.domain.sync

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.BrightPathIndicatorRepository

class SyncBrightPathUseCase(
    private val syncRepository: BrightPathIndicatorRepository,
    private val sessionRepository: SessionRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun sync(): SyncType {
        val ua = session.zonificacion
        return syncRepository.sync(ua)
    }
}
