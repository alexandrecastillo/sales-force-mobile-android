package biz.belcorp.salesforce.modules.auth.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.modules.auth.core.domain.repository.AuthRepository


class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val deviceRepository: DeviceRepository,
    private val manageTopicsUseCase: ManageTopicsUseCase
) : LogoutUseCase {

    override suspend fun logout() {
        deviceRepository.resetFcmTokenPending()
        manageTopicsUseCase.unsubscribeTopics()
        authRepository.logout()
    }
}
