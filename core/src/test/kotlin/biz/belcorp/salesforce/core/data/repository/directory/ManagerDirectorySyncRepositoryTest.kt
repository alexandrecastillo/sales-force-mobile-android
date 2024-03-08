@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.directory

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.repository.directory.cloud.ManagerDirectoryCloudStore
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryDto
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryDataStore
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryTableDataStore
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryMapper
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ManagerDirectorySyncRepositoryTest {

    private val cloudStore = mockk<ManagerDirectoryCloudStore>(relaxed = true)
    private val dataStore = mockk<ManagerDirectoryDataStore>(relaxed = true)
    private val tableDataStore = mockk<ManagerDirectoryTableDataStore>(relaxed = true)
    private val mapper = mockk<ManagerDirectoryMapper>(relaxed = true)
    private val tableMapper = mockk<ManagerDirectoryTableMapper>(relaxed = true)

    private lateinit var repository: ManagerDirectorySyncRepository

    @Before
    fun before() {
        repository = ManagerDirectorySyncDataRepository(cloudStore, dataStore, tableDataStore, mapper, tableMapper)
    }

    @Test
    fun `test sync for Manager Directory`() = runBlockingTest {
        coEvery { cloudStore.getManagerDirectory(any()) } returns createManager()
        repository.sync(createParams())
        coVerify(exactly = 1) { dataStore.saveManagerDirectory(any()) }
    }

    private fun createParams() =
        SyncParams(LlaveUA("", "", ""), "GR", Constant.EMPTY_STRING)

    private fun createManager() = ManagerDirectoryDto(listOf())
}
