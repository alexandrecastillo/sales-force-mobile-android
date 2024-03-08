@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.CollectionMockDataHelper.fetchProfit
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.CollectionMockDataHelper.profitListEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.ProfitCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.ProfitDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.ProfitMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.kpiRequestParamsMock
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ProfitRepositoryTest {

    private val gainDataStore = mockk<ProfitDataStore>(relaxed = true)
    private val gainCloudStore = mockk<ProfitCloudStore>()

    private lateinit var profitDataRepository: ProfitRepository
    private val mapper = ProfitMapper()
    private val queryMapper = KpiQueryMapper()

    @Before
    fun setup() {
        profitDataRepository =
            ProfitDataRepository(gainCloudStore, gainDataStore, mapper, queryMapper)
    }

    @Test
    fun `should find a gain indicator by campaign`() = runBlockingTest {
        coEvery { gainDataStore.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, any()) } returns profitListEntity
        profitDataRepository.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, arrayListOf())
        coVerify { gainDataStore.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a gain indicator by campaign`() = runBlockingTest {
        coEvery { gainDataStore.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, any()) } throws Exception()
        profitDataRepository.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, arrayListOf())
        coVerify { gainDataStore.getProfitByCampaigns(NewCycleIndicatorMock.uaKey, any()) }
    }

    @Test
    fun `should sync gain indicator`() = runBlockingTest {
        coEvery { gainCloudStore.getProfit(any()) } returns fetchProfit()
        coEvery { gainDataStore.saveProfit(any()) } just runs
        profitDataRepository.sync(kpiRequestParamsMock)
        coVerify { gainDataStore.saveProfit(any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't sync gain indicator`() = runBlockingTest {
        coEvery { gainCloudStore.getProfit(any()) } throws Exception()
        profitDataRepository.sync(kpiRequestParamsMock)
        coVerify { gainDataStore.saveProfit(arrayListOf()) }
    }
}
