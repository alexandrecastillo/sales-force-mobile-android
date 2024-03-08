@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.kpis.core.domain.gain

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.campaignMock
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.configuration
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class CollectionIndicatorUseCaseTest {

    private val campaignsRepository = mockk<CampaniasRepository>()
    private val configurationRepository = mockk<ConfigurationRepository>()
    private val sessionRepository = mockk<SessionRepository>()
    private val collectionRepository = mockk<CollectionRepository>()
    private val profitRepository = mockk<ProfitRepository>()
    private val uaInfoUseCase = mockk<UaInfoUseCase>()

    private lateinit var useCase: GetGainUseCase

    @Before
    fun setup() {
        useCase =
            GetGainUseCase(
                campaignsRepository,
                sessionRepository,
                collectionRepository,
                profitRepository,
                configurationRepository,
                uaInfoUseCase
            )
    }

    @Test
    fun `should find a gain indicator`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { sessionRepository.getSession()?.rol } returns Rol.GERENTE_ZONA
        coEvery { sessionRepository.getSession()?.campaign } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaActualSuspend(uaKey) } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns campaignMock
        coEvery { collectionRepository.profitSyncDate() } returns Constant.NUMBER_ZERO_LONG
        coEvery { campaignsRepository.obtenerCampaniasSincrono(any()) } returns arrayListOf(campaignMock)
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { profitRepository.getProfitByCampaigns(any(), any()) } returns arrayListOf()
        coEvery { collectionRepository.getCollectionByCampaigns(any(), any()) } returns arrayListOf()
        useCase.getGainInformation(uaKey)
        coVerify(exactly = 1) { profitRepository.getProfitByCampaigns(any(), any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a gain indicator`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { sessionRepository.getSession()?.rol } returns Rol.GERENTE_ZONA
        coEvery { sessionRepository.getSession()?.campaign } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaActualSuspend(uaKey) } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns campaignMock
        coEvery { collectionRepository.profitSyncDate() } returns Constant.NUMBER_ZERO_LONG
        coEvery { campaignsRepository.obtenerCampaniasSincrono(any()) } returns arrayListOf(campaignMock)
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { profitRepository.getProfitByCampaigns(any(), any()) } throws Exception()
        coEvery { collectionRepository.getCollectionByCampaigns(any(), any()) } returns arrayListOf()
        useCase.getGainInformation(uaKey)
        coVerify(exactly = 1) { profitRepository.getProfitByCampaigns(any(), any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a collection indicator`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { sessionRepository.getSession()?.rol } returns Rol.GERENTE_ZONA
        coEvery { sessionRepository.getSession()?.campaign } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaActualSuspend(uaKey) } returns campaignMock
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns campaignMock
        coEvery { collectionRepository.profitSyncDate() } returns Constant.NUMBER_ZERO_LONG
        coEvery { campaignsRepository.obtenerCampaniasSincrono(any()) } returns arrayListOf(campaignMock)
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { profitRepository.getProfitByCampaigns(any(), any()) } returns arrayListOf()
        coEvery { collectionRepository.getCollectionByCampaigns(any(), any()) } throws Exception()
        useCase.getGainInformation(uaKey)
        coVerify(exactly = 1) { profitRepository.getProfitByCampaigns(any(), any()) }
    }

    @Test()
    fun `should return container with list of children collection`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns campaignMock
        coEvery { uaInfoUseCase.getAssociatedUaListByUaKey(any()) } returns createUas()
        coEvery { collectionRepository.getCollectionsByParent(uaKey, any(),any()) } returns arrayListOf()
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { collectionRepository.profitSyncDate() } returns System.currentTimeMillis()
        useCase.getUaData(uaKey,"21")
        coVerify(exactly = 1) { collectionRepository.getCollectionsByParent(any(), any(),any()) }
    }

    private fun createUas(): List<UaInfo> {
        return listOf(
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.SOCIA_EMPRESARIA,
                person = null,
                campaign = Campania.construirDummy(),
                uaKey = LlaveUA(),
                isCovered = true,
                isThirdPerson = false
            )
        )
    }

}
