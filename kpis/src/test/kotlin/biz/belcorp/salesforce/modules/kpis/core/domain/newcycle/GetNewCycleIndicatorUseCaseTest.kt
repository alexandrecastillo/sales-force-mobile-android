@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.kpis.core.domain.newcycle

import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.BonusInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.BonificationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.configuration
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.createCampaign
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.getSession
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.newCycleListIndicator
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetNewCycleIndicatorUseCaseTest {

    private val newCycleRepository = mockk<NewCycleRepository>()
    private val campaignsRepository = mockk<CampaniasRepository>()
    private val sessionRepository = mockk<SessionRepository>()
    private val configurationRepository = mockk<ConfigurationRepository>()
    private val socialBonusRepository = mockk<BonificationRepository>()

    private lateinit var useCase: GetNewCycleIndicatorUseCase

    @Before
    fun setup() {
        useCase =
            GetNewCycleIndicatorUseCase(newCycleRepository, campaignsRepository, sessionRepository, configurationRepository, socialBonusRepository)
    }

    @Test
    fun `should find a newcycle indicator`() = runBlockingTest {
        val campaignList = arrayListOf(createCampaign(Campania.Periodo.VENTA))
        coEvery { sessionRepository.getSession() } returns getSession()
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { campaignsRepository.obtenerCampaniasSincrono(uaKey) } returns campaignList
        coEvery { socialBonusRepository.getBonusInfo(any(), any()) } returns BonusInfo(ZERO_DECIMAL, ZERO_DECIMAL)
        every { campaignsRepository.obtenerCampaniaActual(any()) } returns createCampaign(Campania.Periodo.VENTA)
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns createCampaign(
            Campania.Periodo.VENTA
        )
        coEvery {
            newCycleRepository.getNewCycleByCampaigns(
                any(),
                any()
            )
        } returns newCycleListIndicator
        useCase.getNewCycleIndicator(uaKey)
        coVerify { newCycleRepository.getNewCycleByCampaigns(any(), any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a newcycle indicator`() = runBlockingTest {
        val campaignList = arrayListOf(createCampaign(Campania.Periodo.VENTA))
        coEvery { sessionRepository.getSession() } returns getSession()
        coEvery { campaignsRepository.obtenerCampaniasSincrono(uaKey) } returns campaignList
        coEvery { newCycleRepository.getNewCycleByCampaigns(uaKey, campaignList.map { it.codigo })
        } throws Exception()
        useCase.getNewCycleIndicator(uaKey)
        coVerify { newCycleRepository.getNewCycleByCampaigns(uaKey, campaignList.map { it.codigo }) }
    }

    @Test
    fun `should find a newcycle grid data`() = runBlockingTest {
        val campaignList = arrayListOf(createCampaign(Campania.Periodo.VENTA))
        coEvery { sessionRepository.getSession() } returns getSession()
        coEvery {
            newCycleRepository.getNewCycleByCampaigns(uaKey, campaignList.map { it.codigo })
        } returns newCycleListIndicator
        coEvery { socialBonusRepository.getBonusInfo(any(), any()) } returns BonusInfo(ZERO_DECIMAL, ZERO_DECIMAL)
        coEvery { campaignsRepository.obtenerCampaniaActual(any()) } returns createCampaign(Campania.Periodo.VENTA)
        coEvery { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns createCampaign(
            Campania.Periodo.VENTA
        )
        coEvery { configurationRepository.getConfiguration(uaKey) } returns configuration
        coEvery { campaignsRepository.obtenerCampaniasSincrono(uaKey) } returns campaignList
        coEvery { newCycleRepository.getNewCycleListByParent(any(), any()) } returns newCycleListIndicator
        useCase.getNewCycleGridData(uaKey)
        coVerify { newCycleRepository.getNewCycleListByParent(any(), any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a newcycle grid data`() = runBlockingTest {
        val campaignList = arrayListOf(createCampaign(Campania.Periodo.VENTA))
        coEvery { sessionRepository.getSession() } returns getSession()
        coEvery { campaignsRepository.obtenerCampaniasSincrono(uaKey) } returns campaignList
        coEvery { newCycleRepository.getNewCycleListByParent(any(), arrayListOf()) } throws Exception()
        useCase.getNewCycleGridData(uaKey)
        coVerify { newCycleRepository.getNewCycleListByParent(any(), arrayListOf()) }
    }
}
