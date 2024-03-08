package biz.belcorp.salesforce.modules.digital.core.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.readString
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.DigitalDataRepository
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.DigitalCloudStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalDto
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.data.DigitalDataStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.mappers.DigitalDataMapper
import biz.belcorp.salesforce.modules.digital.core.domain.repository.DigitalRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class DigitalDataRepositoryTest {

    private val dataStore: DigitalDataStore = mockk(relaxed = true)
    private val cloudStore: DigitalCloudStore = mockk(relaxed = true)
    private val mapper: DigitalDataMapper = DigitalDataMapper()

    private lateinit var repository: DigitalRepository

    @Before
    fun before() {
        repository = DigitalDataRepository(dataStore, cloudStore, mapper)
    }

    @Test
    fun `Sync`() = runBlockingTest {

        val country = "PE"
        val campaign = "202014"
        val uaKey = LlaveUA("80", "8012", "A")

        coEvery { cloudStore.getDigitalInfo(any()) } returns getOnlineStoreMockData()

        repository.sync(country, campaign, uaKey)

        verify(exactly = 1) { dataStore.saveDigitalInfo(any()) }

    }

    @Test
    fun `Get Info`() = runBlockingTest {

        val campaign = "202014"
        val uaKey = LlaveUA("80", "8012", "A")
        val mockData = mapper.map(getOnlineStoreMockData()).first()

        coEvery { dataStore.getDigitalInfo(campaign, uaKey) } returns mockData

        val result = repository.getDigitalInfo(campaign, uaKey)

        result.region shouldBeEqualTo mockData.region
        result.zone shouldBeEqualTo mockData.zone
        result.section shouldBeEqualTo mockData.section

    }

    @Test
    fun `Get Info by Parent`() = runBlockingTest {

        val campaign = "202014"
        val uaKey = LlaveUA("80", "8012", "A")
        val mockData = mapper.map(getOnlineStoreMockData())

        coEvery { dataStore.getDigitalInfoByParent(campaign, uaKey) } returns mockData

        val result = repository.getDigitalInfoByParent(campaign, uaKey)

        result.size shouldEqualTo mockData.size

    }

    private fun getOnlineStoreMockData(): DigitalDto {
        val jsonString = readString("core/data/online_store.json")
        return JsonEncodedDefault.decodeFromString(DigitalDto.serializer(), jsonString)
    }

}
