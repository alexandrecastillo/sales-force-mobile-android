@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class GetGroupSegUseCaseTest {

    private val groupSegRepository = mockk<GroupSegRepository>()

    private lateinit var useCase: GetGroupSegUseCase

    @Before
    fun setup() {
        useCase = GetGroupSegUseCase(groupSegRepository)
    }

    @Test
    fun `test sync`() = runBlockingTest {
        coEvery { groupSegRepository.getGroups() } returns emptyList()
        useCase.get()
        coVerify(exactly = 1) { groupSegRepository.getGroups() }
    }

}
