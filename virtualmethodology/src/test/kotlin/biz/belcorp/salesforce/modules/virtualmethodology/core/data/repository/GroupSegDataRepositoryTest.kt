@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository

import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.mappers.GroupSegDataMapper
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class GroupSegDataRepositoryTest {

    private val groupSegDataStore = mockk<GroupSegDataStore>()
    private val segmentationDataStore = mockk<SegmentationDataStore>()
    private val groupSegCloudStore = mockk<GroupSegCloudStore>()
    private val groupSegDataMapper = GroupSegDataMapper()

    private lateinit var repository: GroupSegDataRepository

    @Before
    fun setup() {
        repository = GroupSegDataRepository(
            groupSegDataStore,
            segmentationDataStore,
            groupSegCloudStore,
            groupSegDataMapper
        )
    }

    @Test
    fun `test syncGroups`() = runBlockingTest {
        coEvery { groupSegCloudStore.syncGroupSegmentacion(any(), any()) } returns mockk {
            every { first } returns emptyList()
            every { second } returns emptyList()
        }
        coEvery { groupSegDataStore.save(any()) } just runs
        coEvery { segmentationDataStore.save(any()) } just runs
        repository.syncGroups("PE", "01/1212/A")
        verify(exactly = 1) { groupSegDataStore.save(any()) }
        verify(exactly = 1) { segmentationDataStore.save(any()) }
    }

}
