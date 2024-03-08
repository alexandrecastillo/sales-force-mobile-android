package biz.belcorp.salesforce.core.domain.usecases.device

import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository


class UpdateTokenUseCase(
    private val deviceRepository: DeviceRepository,
    private val sesionRepository: SessionRepository
) {

    private val session by lazy { sesionRepository.getSession() }

    suspend fun updateToken(token: String) {
        deviceRepository.saveToken(token, session)
    }

    suspend fun syncIfNeeded() {
        deviceRepository.syncTokenIfNeeded(session)
    }

}
