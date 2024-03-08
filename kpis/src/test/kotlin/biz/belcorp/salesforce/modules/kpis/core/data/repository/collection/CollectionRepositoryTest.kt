package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.CollectionCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.CollectionDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.CollectionMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionRepositoryTest {

    private val collectionCloudStore = mockk<CollectionCloudStore>()
    private val collectionDataStore = mockk<CollectionDataStore>()
    private val syncPreferences = mockk<SyncSharedPreferences>()

    private lateinit var repository: CollectionRepository
    private val mapper = CollectionMapper()
    private val queryMapper = KpiQueryMapper()

    @Before()
    fun setup() {
        repository = CollectionDataRepository(
            collectionCloudStore,
            collectionDataStore,
            syncPreferences,
            mapper,
            queryMapper
        )
    }

    @Test
    fun `test get Collections by campaigns`() = runBlockingTest {
        val uaKey = LlaveUA("80", "8020", null, 0)
        coEvery {
            collectionDataStore.getCollectionByCampaigns(any(), any())
        } returns collectionByCampaignMock

        val result = repository.getCollectionByCampaigns(uaKey, listOf())

        result.size shouldEqual 1
        result.first().ordersRange.size shouldEqual 1
    }

    @Test
    fun `test get children collection by campaign`() = runBlockingTest {
        val uaKey = LlaveUA("80", "8020", null, 0)
        coEvery {
            collectionDataStore.getCollectionsByParent(any(), any(),any())
        } returns collectionByCampaignMock

        val result = repository.getCollectionsByParent(uaKey, "202005","21")

        result.size shouldEqual 1
        result.first().ordersRange.size shouldEqual 1
    }

    private val collectionByCampaignMock by lazy {
        listOf<CollectionEntity>(
            mockk {
                every { campaign } returns EMPTY_STRING
                every { profile } returns EMPTY_STRING
                every { region } returns EMPTY_STRING
                every { zone } returns EMPTY_STRING
                every { section } returns EMPTY_STRING
                every { percentage } returns ZERO_DECIMAL
                every { invoicedSale } returns ZERO_DECIMAL
                every { amountCollected } returns ZERO_DECIMAL
                every { debtorConsultants } returns NUMBER_ZERO
                every { ordersTotalGained } returns ZERO_DECIMAL
                every { ordersMinimalCollectionPercentage } returns ZERO_DECIMAL
                every { ordersTotalCollected } returns NUMBER_ZERO
                every { ordersTotal } returns NUMBER_ZERO
                every { ordersRange } returns mockk {
                    every { size } returns 1
                    every { get(any<Int>()) } returns mockk {
                        every { range } returns EMPTY_STRING
                        every { collected } returns NUMBER_ZERO
                        every { total } returns NUMBER_ZERO
                        every { position } returns NUMBER_ZERO
                    }
                }
            }
        )
    }

}
