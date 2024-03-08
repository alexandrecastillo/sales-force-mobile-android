@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.terms

import biz.belcorp.salesforce.core.data.repository.terms.cloud.TermsConditionsCloudStore
import biz.belcorp.salesforce.core.data.repository.terms.data.TermsConditionsDataStore
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams.Companion.TYPE_QUERY_CONSULTANT_CODE
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class TermsConditionsDataRepositoryTest {

    private val dataStore = mockk<TermsConditionsDataStore>()
    private val cloudStore = mockk<TermsConditionsCloudStore>()
    private val mapper = TermsConditionsMapper()

    private lateinit var repository: TermsConditionsDataRepository

    @Before
    fun setup() {
        repository = TermsConditionsDataRepository(dataStore, cloudStore, mapper)
    }

    @Test
    fun `test getTermsConditions`() = runBlockingTest {
        every { dataStore.getTermsConditions() } returns TermsConditionMockDataHelper.getTermsEntities()
        val list = repository.getTermsConditions()
        list shouldHaveSize 3
    }

    @Test
    fun `test syncTermsConditions`() = runBlockingTest {
        coEvery { cloudStore.downloadTermsConditions(any()) } returns emptyList()
        every { dataStore.saveTermsConditions(any()) } just runs
        repository.syncTermsConditions("PE", "SE")
        verify(exactly = 1) { dataStore.saveTermsConditions(any()) }
    }

    @Test
    fun `test syncApproveTermsConditions`() = runBlockingTest {
        coEvery { cloudStore.downloadApproveTerms(any()) } returns emptyList()
        every { dataStore.saveApproveTermsConditions(any()) } just runs
        repository.syncApprovedTermsConditions("PE", "SE", "0246329", TYPE_QUERY_CONSULTANT_CODE)
        verify(exactly = 1) { dataStore.saveApproveTermsConditions(any()) }
    }

    @Test
    fun `test saveApprovedTerm`() = runBlockingTest {
        coEvery { cloudStore.saveApproveTerms(any()) } returns emptyList()
        every { dataStore.saveApproveTermsConditions(any()) } just runs
        repository.saveApprovedTerm(TermsConditionMockDataHelper.getApproveParams())
        verify(exactly = 1) { dataStore.saveApproveTermsConditions(any()) }
    }

    @Test
    fun `test isTermApproved`() = runBlockingTest {
        every { dataStore.getApproveTerms(any()) } returns TermsConditionMockDataHelper.getApproveTerm()
        val list = repository.isTermApproved(ApproveTermsParams.LINK_SE)
        list shouldEqualTo true
    }

    @Test
    fun `test isTermBlocked`() = runBlockingTest {
        every { dataStore.getApproveTerms(any()) } returns TermsConditionMockDataHelper.getApproveTerm()
        val list = repository.isTermBlocked(ApproveTermsParams.LINK_SE)
        list shouldEqualTo true
    }

    @Test
    fun `test getTermUrl`() = runBlockingTest {
        every { dataStore.getTerm(any()) } returns TermsConditionMockDataHelper.getTerm()
        val url = repository.getTermUrl(ApproveTermsParams.LINK_SE)
        url shouldNotBe ""
    }

}
