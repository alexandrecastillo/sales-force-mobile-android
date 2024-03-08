@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.core.data.repository.options

import biz.belcorp.salesforce.base.core.data.repository.options.cloud.OptionsCloudStore
import biz.belcorp.salesforce.base.core.data.repository.options.data.OptionsDataStore
import biz.belcorp.salesforce.base.core.data.repository.options.mappers.OptionsMapper
import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test

class OptionsRepositoryTest {

    private val cloudStoreMock = mockk<OptionsCloudStore>(relaxed = true)
    private val dataStoreMock = mockk<OptionsDataStore>(relaxed = true)
    private val sessionRepository = mockk<SessionRepository>()
    private val mapper = OptionsMapper()

    private lateinit var repository: OptionsRepository

    @Before
    fun before() {
        repository =
            OptionsDataRepository(cloudStoreMock, dataStoreMock, mapper)
    }

    @Test
    fun `test sync options`() = runBlockingTest {
        val uaKey = LlaveUA("80", "8020", "A", 0)
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { cloudStoreMock.getOptions(any()) } returns OptionsMockDataHelper.getOptionsQueryData()

        repository.sync("PE", "SE", uaKey)

        verify(exactly = 1) { dataStoreMock.saveOptions(any()) }
        verify(exactly = 1) { dataStoreMock.saveSubOptions(any()) }
    }

    @Test
    fun `test get menu options`() = runBlockingTest {
        val optionType = "MP"
        val dataMock = OptionsMockDataHelper.getMenuOptionsEntities(optionType)
        every { dataStoreMock.getOptions(optionType) } returns dataMock
        val menuOptions = repository.getMenuOptions()
        menuOptions shouldHaveSize 5
    }

    @Test
    fun `test get sub options`() = runBlockingTest {
        val parentCode = 5
        val optionType = "ML"
        val dataMock = OptionsMockDataHelper.getMenuSubOptionsEntities(parentCode, optionType)
        every { dataStoreMock.getSubOptions(parentCode, optionType) } returns dataMock
        val menuOptions = repository.getMenuSubOptions(parentCode)
        menuOptions shouldHaveSize 4
    }

    @Test
    fun `test get shortcut options`() = runBlockingTest {
        val optionType = "SC"
        val dataMock = OptionsMockDataHelper.getMenuOptionsEntities(optionType)
        every { dataStoreMock.getOptions(optionType) } returns dataMock
        val menuOptions = repository.getShortcutOptions()
        menuOptions shouldHaveSize 6
    }

}
