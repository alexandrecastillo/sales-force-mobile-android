@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.core.data.repository.uas

import biz.belcorp.salesforce.core.data.network.dto.UaResponse
import biz.belcorp.salesforce.core.data.repository.uainfo.UaInfoDataRepository
import biz.belcorp.salesforce.core.data.repository.uainfo.cloud.UaInfoCloudStore
import biz.belcorp.salesforce.core.data.repository.uainfo.data.UaInfoDataStore
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.RegionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.SeccionTableMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.UaInfoMapper
import biz.belcorp.salesforce.core.data.repository.uainfo.mappers.ZonaTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.random.Random


class UaRepositoryTest {

    private val cloudStore = mockk<UaInfoCloudStore>(relaxed = true)
    private val dataStore = mockk<UaInfoDataStore>(relaxed = true)
    private val person = mockk<Person>()
    private val mapper = UaInfoMapper()
    private val seccionTableMapper = SeccionTableMapper()
    private val zonaTableMapper = ZonaTableMapper()
    private val regionTableMapper = RegionTableMapper()

    private lateinit var infoRepository: UaInfoRepository


    @Before
    fun before() {
        every { person.firstName } returns "testname"
        infoRepository = UaInfoDataRepository(
            cloudStore,
            dataStore,
            mapper,
            seccionTableMapper,
            zonaTableMapper,
            regionTableMapper
        )
    }

    @Test
    fun `test sync uas region`() = runBlockingTest {
        every { person.role } returns Rol.GERENTE_REGION
        every { person.uaKey } returns createUaKey()
        coEvery { cloudStore.getUa(any(), any()) } returns mockUaResponse(4)

        infoRepository.sync("PE", person)

        verify(exactly = 1) { dataStore.saveUa(any()) }
        verify(exactly = 1) { dataStore.saveChildrenUas(any()) }
    }

    private fun mockUaResponse(size: Int): List<UaResponse> {
        val response = arrayListOf<UaResponse>()
        for (i in 0 until size) {
            response.add(mockResponse())
        }
        return response
    }

    private fun mockResponse(): UaResponse {
        return UaResponse(
            regionId = Random.nextLong(),
            regionCode = "${Random.nextInt()}",
            regionDescription = "LIMA ${Random.nextInt()}",
            regionManager = "Carmen ${Random.nextInt()}",
            managerEmail = "c${Random.nextInt()}@belcorp.biz"
        )
    }

    private fun createUaKey() = LlaveUA("80", null, null, null)

}



