@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams.Companion.TYPE_QUERY_CONSULTANT_CODE
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SyncTermsConditionsUseCaseTest {

    private val sessionRepository = mockk<SessionRepository>(relaxed = true)
    private val materialRepository = mockk<TermsConditionsRepository>(relaxed = true)
    private lateinit var useCase: SyncTermsConditionsUseCase

    @Before
    fun setup() {
        useCase = SyncTermsConditionsUseCase(sessionRepository, materialRepository)
    }

    @Test
    fun `test syncTerms`() = runBlockingTest {
        every { sessionRepository.getSession() } returns mockk {
            every { codigoRol } returns "SE"
            every { countryIso } returns "PE"
        }
        coEvery { materialRepository.syncTermsConditions("PE", "SE") } just runs
        useCase.sync()
        coVerify(exactly = 1) { materialRepository.syncTermsConditions("PE", "SE") }
    }

    @Test
    fun `test syncApprovedTerms`() = runBlockingTest {
        every { sessionRepository.getSession() } returns mockk {
            every { codigoRol } returns "SE"
            every { countryIso } returns "PE"
            every { codigoConsultora } returns "0246329"
        }
        coEvery {
            materialRepository.syncApprovedTermsConditions(
                "PE",
                "SE",
                "0246329",
                TYPE_QUERY_CONSULTANT_CODE
            )
        } just runs
        useCase.syncApprovedTerms()
        coVerify(exactly = 1) {
            materialRepository.syncApprovedTermsConditions(
                "PE",
                "SE",
                "0246329",
                TYPE_QUERY_CONSULTANT_CODE
            )
        }
    }


}
