@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository
import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class FetchRemoteConfigUseCaseTest {

    private val configRepoMock = mockk<RemoteConfigRepository>(relaxed = true)

    private lateinit var useCase: FetchRemoteConfigUseCase

    @Before
    fun setup() {
        useCase = FetchRemoteConfigUseCase(configRepoMock)
    }

    @Test
    fun `test fetch remote config`() = runBlockingTest {
        useCase.fetchConfig()
        coVerify { configRepoMock.fetchConfig() }
    }

}
