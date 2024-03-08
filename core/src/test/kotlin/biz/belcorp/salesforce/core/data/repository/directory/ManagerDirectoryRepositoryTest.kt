@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.directory

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryDataStore
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryMapper
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ManagerDirectoryRepositoryTest {

    private val dataStore = mockk<ManagerDirectoryDataStore>(relaxed = true)
    private val mapper = mockk<ManagerDirectoryMapper>(relaxed = true)
    private lateinit var repository: ManagerDirectoryRepository

    @Before
    fun before() {
        repository = ManagerDirectoryDataRepository(dataStore, mapper)
    }

    @Test
    fun `test to get all Manager Directory`() = runBlockingTest {
        coEvery { dataStore.getManagers() } returns emptyList()
        repository.getManagers()
        verify { dataStore.getManagers() }
    }

    @Test
    fun `test to get Manager by Id`() = runBlockingTest {
        coEvery { dataStore.getManager(any<Int>()) } returns createManager()
        repository.getManager(20)
        verify { dataStore.getManager(20) }
    }

    @Test
    fun `test to get Manager by UA`() = runBlockingTest {
        val uaKey = createUaKey()
        coEvery { dataStore.getManager(any<LlaveUA>()) } returns createManager()
        repository.getManager(uaKey)
        verify { dataStore.getManager(uaKey) }
    }

    private fun createUaKey() = LlaveUA(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, 1)

    private fun createManager() = ManagerDirectoryEntity(
        id = 0,
        birthday = EMPTY_STRING,
        document = EMPTY_STRING,
        firstName = EMPTY_STRING,
        secondName = EMPTY_STRING,
        secondSurname = EMPTY_STRING,
        email = EMPTY_STRING,
        productivity = EMPTY_STRING,
        description = EMPTY_STRING,
        code = EMPTY_STRING,
        cellphoneNumber = EMPTY_STRING,
        telephoneNumber = EMPTY_STRING,
        fullName = EMPTY_STRING,
        surname = EMPTY_STRING,
        userName = EMPTY_STRING,
        zone = EMPTY_STRING,
        region = EMPTY_STRING,
        profile = EMPTY_STRING,
        consultantId = 1,
        isNew = false,
        campaignsAsNew = 0
    )
}
