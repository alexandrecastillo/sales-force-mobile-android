@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.consultants.consultants.core.data

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.ConsultantsDataSyncRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.ConsultantsCloudStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsMapper
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsTableDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsTableMapper
import biz.belcorp.salesforce.modules.consultants.feature.filters.DataCreator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test

class ConsultantsSyncRepositoryTest {

    private val dataStoreMock = mockk<ConsultantsDataStore>(relaxed = true)
    private val tableDataStoreMock = mockk<ConsultantsTableDataStore>(relaxed = true)
    private val cloudDataMock = mockk<ConsultantsCloudStore>(relaxed = true)
    private val syncPreferencesMock = mockk<SyncSharedPreferences>(relaxed = true)

    private lateinit var repository: ConsultantsDataSyncRepository
    private lateinit var mapper: ConsultantsMapper
    private lateinit var tableMapper: ConsultantsTableMapper

    @Before
    fun onBefore() {
        mapper = ConsultantsMapper()
        tableMapper = ConsultantsTableMapper()
        repository = ConsultantsDataSyncRepository(
            cloudDataMock,
            dataStoreMock,
            tableDataStoreMock,
            mapper,
            tableMapper,
            syncPreferencesMock,
            SyncOnDemandRepository.SyncField.Consultants
        )
    }

    @Test
    fun `obtain consultants from cloud store - case-updated test`() = runBlockingTest {
        val uaKey = getUaKey()
        every { syncPreferencesMock.consultantsSyncDate } returns 0
        val mock = DataCreator.getConsultantsMockData()
        coEvery { cloudDataMock.getConsultants(any()) } returns mock
        val mockDigital = DataCreator.getConsultantsOnlineStoreMockData()
        coEvery { cloudDataMock.getDigitalConsultants(any()) } returns mockDigital

        val params = SyncParams(uaKey, "CO", "202003", "F", false)
        val response = repository.sync(params)

        response shouldBeInstanceOf SyncType.Updated::class
    }

    @Test
    fun `obtain consultants from cloud store - case-unsuccessful test`() = runBlockingTest {
        val uaKey = getUaKey()
        every { syncPreferencesMock.consultantsSyncDate } returns System.currentTimeMillis()
        val mock = DataCreator.getConsultantsMockData()
        coEvery { cloudDataMock.getConsultants(any()) } returns mock

        val params = SyncParams(uaKey, "CO", "202003", "F", false)
        val response = repository.sync(params)

        response shouldBeInstanceOf SyncType.None::class
    }

    private fun getUaKey() = LlaveUA(
        codigoRegion = "14",
        codigoZona = "1416",
        codigoSeccion = "A",
        consultoraId = null
    )
}
