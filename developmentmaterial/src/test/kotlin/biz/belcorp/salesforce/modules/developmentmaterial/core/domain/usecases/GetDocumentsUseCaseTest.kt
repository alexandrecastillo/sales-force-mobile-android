@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class GetDocumentsUseCaseTest {

    private val repository = mockk<MaterialesDesarrolloRepository>(relaxed = true)
    private lateinit var useCase: GetDocumentsUseCase

    @Before
    fun setup() {
        useCase = GetDocumentsUseCase(repository)
    }

    @Test
    fun `test get in GetDocumentsUseCase`() = runBlockingTest {
        coEvery { repository.getDocuments() } returns emptyList()
        useCase.get()
        coVerify(exactly = 1) { repository.getDocuments() }
    }

}
