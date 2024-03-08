@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle

import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.NewCycleCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.data.NewCycleDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.mapper.NewCycleMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.newCycleListEntity
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.any
import org.junit.Before
import org.junit.Test

class NewCycleDataRepositoryTest {

    private val newCycleDataStore = mockk<NewCycleDataStore>()
    private val newCycleCloudStore = mockk<NewCycleCloudStore>()

    private lateinit var newCycleDataRepository: NewCycleRepository
    private val mapper = NewCycleMapper()
    private val queryMapper = KpiQueryMapper()

    @Before
    fun setup() {
        newCycleDataRepository =
            NewCycleDataRepository(newCycleDataStore, newCycleCloudStore, mapper, queryMapper)
    }

    @Test
    fun `should find a newcycle indicator by campaign`() = runBlockingTest {
        coEvery { newCycleDataStore.getNewCyclesByCampaigns(any(), arrayListOf()) } returns newCycleListEntity
        newCycleDataRepository.getNewCycleByCampaigns(uaKey, arrayListOf())
        coVerify(exactly = 1) { newCycleDataStore.getNewCyclesByCampaigns(any(), arrayListOf()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a newcycle indicator by campaign`() = runBlockingTest {
        coEvery { newCycleDataStore.getNewCyclesByCampaigns(any(), arrayListOf()) } throws Exception()
        newCycleDataRepository.getNewCycleByCampaigns(uaKey, arrayListOf())
        coVerify { newCycleDataStore.getNewCyclesByCampaigns(any(), arrayListOf()) }
    }

    @Test
    fun `should find a newcycle indicator by role`() = runBlockingTest {
        coEvery { newCycleDataStore.getNewCycleListByParent(any(), arrayListOf()) } returns newCycleListEntity
        newCycleDataRepository.getNewCycleListByParent(any(), arrayListOf())
        coVerify(exactly = 1) { newCycleDataStore.getNewCycleListByParent(any(), arrayListOf()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a newcycle indicator by role`() = runBlockingTest {
        coEvery { newCycleDataStore.getNewCycleListByParent(any(), arrayListOf()) } throws Exception()
        newCycleDataRepository.getNewCycleListByParent(any(), arrayListOf())
        coVerify { newCycleDataStore.getNewCycleListByParent(any(), arrayListOf()) }
    }

    @Test
    fun `test for getting new cycle indicator by campaigns`() = runBlockingTest {
        coEvery { newCycleDataStore.getNewCyclesByCampaigns(any(), any()) } returns emptyList()
        newCycleDataRepository.getNewCycleByCampaigns(any(), any())
        verify(exactly = 1) { newCycleDataStore.getNewCyclesByCampaigns(any(), any()) }
    }
}
