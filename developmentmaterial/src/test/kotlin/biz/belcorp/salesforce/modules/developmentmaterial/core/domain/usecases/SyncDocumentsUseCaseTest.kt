@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class SyncDocumentsUseCaseTest {

    private val sessionRepository = mockk<SessionRepository>(relaxed = true)
    private val materialRepository = mockk<MaterialesDesarrolloRepository>(relaxed = true)
    private lateinit var useCase: SyncDocumentsUseCase

    @Before
    fun setup() {
        useCase = SyncDocumentsUseCase(sessionRepository, materialRepository)
    }

    @Test
    fun `test sync in SyncDocumentsUseCase`() = runBlockingTest {
        every { sessionRepository.getSession() } returns mockk {
            every { codigoRol } returns "CO"
        }
        coEvery { materialRepository.syncDocuments("CO") } just runs
        useCase.sync()
        coVerify(exactly = 1) { materialRepository.syncDocuments("CO") }
    }

}
