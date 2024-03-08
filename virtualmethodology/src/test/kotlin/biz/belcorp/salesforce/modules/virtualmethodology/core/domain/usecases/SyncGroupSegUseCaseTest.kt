@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class SyncGroupSegUseCaseTest {

    private val sessionRepository = mockk<SessionRepository>()
    private val groupSegRepository = mockk<GroupSegRepository>()

    private lateinit var useCase: SyncGroupSegUseCase

    @Before
    fun setup() {
        useCase = SyncGroupSegUseCase(sessionRepository, groupSegRepository)
    }

    @Test
    fun `test sync`() = runBlockingTest {
        coEvery { sessionRepository.getSession() } returns mockk {
            every { countryIso } returns "PE"
            every { zonificacion } returns "01/1212/A"
        }
        coEvery { groupSegRepository.syncGroups(any(), any()) } just runs
        useCase.sync()
        coVerify(exactly = 1) { groupSegRepository.syncGroups(any(), any()) }
    }

}
