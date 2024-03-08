@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.data.repository.terms.TermsConditionMockDataHelper
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class TermsConditionsUseCaseTest {

    private val repository = mockk<TermsConditionsRepository>(relaxed = true)
    private val sessionRepository = mockk<SessionRepository>(relaxed = true)
    private val campaignsRepository = mockk<CampaniasRepository>(relaxed = true)
    private lateinit var useCase: TermConditionsUseCase

    @Before
    fun setup() {
        useCase = TermConditionsUseCase(sessionRepository,campaignsRepository,repository)
    }

    @Test
    fun `test getTerms in TermsConditionsUseCase`() = runBlockingTest {
        coEvery { repository.getTermsConditions() } returns TermsConditionMockDataHelper.getTermsConditionsModel()
        useCase.getTerms()
        coVerify(exactly = 1) { repository.getTermsConditions() }
    }

    @Test
    fun `test getTerm in TermsConditionsUseCase`() = runBlockingTest {
        coEvery { repository.getTermUrl(any()) } returns TermsConditionMockDataHelper.getTermUrl()
        useCase.getTerm(ApproveTermsParams.LINK_SE)
        coVerify(exactly = 1) { repository.getTermUrl(any()) }
    }

    @Test
    fun `test isTermApproved in TermsConditionsUseCase`() = runBlockingTest {
        coEvery { repository.isTermApproved(any()) } returns TermsConditionMockDataHelper.isTermApproved()
        useCase.isTermApproved(ApproveTermsParams.LINK_SE)
        coVerify(exactly = 1) { repository.isTermApproved(any()) }
    }

    @Test
    fun `test isTermBlocked in TermsConditionsUseCase`() = runBlockingTest {
        coEvery { repository.isTermBlocked(any()) } returns TermsConditionMockDataHelper.isTermBlocked()
        useCase.isTermBlocked(ApproveTermsParams.LINK_SE)
        coVerify(exactly = 1) { repository.isTermBlocked(any()) }
    }

    @Test
    fun `test saveApprovedTerms in TermsConditionsUseCase`() = runBlockingTest {
        coEvery { repository.saveApprovedTerm(any()) } returns true
        useCase.saveApprovedTerms(ApproveTermsParams.LINK_SE)
        coVerify(exactly = 1) { repository.saveApprovedTerm(any()) }
    }

}
