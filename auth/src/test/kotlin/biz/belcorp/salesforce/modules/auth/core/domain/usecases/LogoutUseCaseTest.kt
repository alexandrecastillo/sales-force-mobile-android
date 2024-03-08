@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.auth.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.modules.auth.core.domain.repository.AuthRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class LogoutUseCaseTest {

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private val deviceRepository = mockk<DeviceRepository>(relaxed = true)
    private val manageTopicUseCase = mockk<ManageTopicsUseCase>(relaxed = true)

    private lateinit var useCase: LogoutUseCase

    @Before
    fun setup() {
        useCase = LogoutUseCase(authRepository, deviceRepository, manageTopicUseCase)
    }

    @Test
    fun `test logout`() = runBlockingTest {
        useCase.logout()
        coVerify(exactly = 1) { deviceRepository.resetFcmTokenPending() }
        coVerify(exactly = 1) { manageTopicUseCase.unsubscribeTopics() }
        coVerify(exactly = 1) { authRepository.logout() }
    }

}
