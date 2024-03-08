@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders

import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.SaleOrdersCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.data.SaleOrdersDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.campaignMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.kpiRequestParamsMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.uaKeyMock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.any
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test

class SaleOrdersRepositoryTest {

    private val cloudStoreMock = mockk<SaleOrdersCloudStore>(relaxed = true)
    private val dataStoreMock = mockk<SaleOrdersDataStore>(relaxed = true)
    private val mapper = SaleOrdersMapper()
    private val queryMapper = KpiQueryMapper()

    private lateinit var repository: SaleOrdersRepository

    @Before
    fun before() {
        repository =
            SaleOrdersDataRepository(cloudStoreMock, dataStoreMock, mapper, queryMapper)
    }

    @Test
    fun `test sync salesorders`() = runBlockingTest {
        coEvery { cloudStoreMock.getSalesOrders(any()) } returns SalesOrdersMockDataHelper.getSalesOrdersQueryData()
        repository.sync(kpiRequestParamsMock)
        verify(exactly = 1) { dataStoreMock.saveSaleOrders(any()) }
    }


    @Test
    fun `test for getting sale y orders indicator by campaigns`() = runBlockingTest {
        coEvery { dataStoreMock.getSalesOrdersByCampaigns(any(), any()) } returns emptyList()
        repository.getSalesOrdersByCampaigns(any(), any())
        verify(exactly = 1) { dataStoreMock.getSalesOrdersByCampaigns(any(), any()) }
    }
 }
