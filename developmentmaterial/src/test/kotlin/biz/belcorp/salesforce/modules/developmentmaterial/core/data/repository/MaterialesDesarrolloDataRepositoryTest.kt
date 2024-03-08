@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository

import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test


class MaterialesDesarrolloDataRepositoryTest {

    private val dataStore = mockk<MaterialesDesarrolloDataStore>()
    private val cloudStore = mockk<MaterialesDesarrolloCloudStore>()
    private val mapper = MaterialesDesarrolloMapper()

    private lateinit var repository: MaterialesDesarrolloDataRepository

    @Before
    fun setup() {
        repository = MaterialesDesarrolloDataRepository(dataStore, cloudStore, mapper)
    }

    @Test
    fun `test getDocuments`() = runBlockingTest {
        every { dataStore.getDocuments() } returns listOf(
            mockk {
                every { id } returns 1
                every { nombreDocumento } returns "00000000"
                every { url } returns ""
                every { rol } returns "CO"
            },
            mockk {
                every { id } returns 2
                every { nombreDocumento } returns "00000001"
                every { url } returns ""
                every { rol } returns "CO"
            }
        )
        val list = repository.getDocuments()
        list shouldHaveSize 2
    }

    @Test
    fun `test syncDocuments`() = runBlockingTest {
        coEvery { cloudStore.downloadDocuments(any()) } returns emptyList()
        every { dataStore.saveDocuments(any()) } just runs
        repository.syncDocuments("CO")
        verify(exactly = 1) { dataStore.saveDocuments(any()) }
    }

}
