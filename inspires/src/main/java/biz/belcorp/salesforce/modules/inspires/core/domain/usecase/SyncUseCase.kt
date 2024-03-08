package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.sync.SyncRepository

class SyncUseCase(
    private val syncRepository: SyncRepository,
    private val sesionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sesionRepository.getSession()) }

    suspend fun sync(): SyncType {
        val ua = session.zonificacion
        return syncRepository.sync(ua)
    }

}
