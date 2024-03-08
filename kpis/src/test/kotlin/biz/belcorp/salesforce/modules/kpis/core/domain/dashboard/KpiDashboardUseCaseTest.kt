@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.core.domain.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.CollectionMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiRules
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard.KpiDashboardUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboardParams
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.any
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class KpiDashboardUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val saleOrdersRepository = mockk<SaleOrdersRepository>(relaxed = true)
    private val collectionRepository = mockk<CollectionRepository>(relaxed = true)
    private val newCycleRepository = mockk<NewCycleRepository>(relaxed = true)
    private val capitalizationRepository = mockk<CapitalizationRepository>(relaxed = true)
    private val configurationRepository = mockk<ConfigurationRepository>(relaxed = true)
    private val sessionRepository = mockk<SessionRepository>()
    private val campaignRepository = mockk<CampaniasRepository>(relaxed = true)

    private lateinit var kpiDashboardUseCase: KpiDashboardUseCase

    @Before
    fun setup() {
        every { sessionRepository.getSession() } returns mockk {
            every { rol } returns Rol.SOCIA_EMPRESARIA
            every { llaveUA } returns createUaKey()
            every { person } returns mockk {
                every { role } returns Rol.SOCIA_EMPRESARIA
            }
            every { pais } returns Pais.COSTARICA
        }

        coEvery { configurationRepository.getConfiguration(any()) } returns mockk {
            every { country } returns Pais.COSTARICA.codigoIso
            every { isPdv } returns true
        }

        every { campaignRepository.obtenerCampaniasSincrono(any()) } returns createCampaigns()

        coEvery { saleOrdersRepository.getSalesOrdersByCampaigns(any(), any()) } returns emptyList()
        coEvery {
            collectionRepository.getCollectionByCampaigns(any(), any())
        } returns getCollectionMockData()
        coEvery {
            newCycleRepository.getNewCycleByCampaigns(any(), any())
        } returns emptyList()
        coEvery {
            capitalizationRepository.getKpiDataByCampaignsAndUa(any(), any())
        } returns emptyList()

        kpiDashboardUseCase = KpiDashboardUseCase(
            saleOrdersRepository,
            collectionRepository,
            newCycleRepository,
            capitalizationRepository,
            configurationRepository,
            sessionRepository,
            campaignRepository
        )
    }

    @Test
    fun `test for KpiInformation requests`() = runBlockingTest {
        kpiDashboardUseCase.getKpiInformation(createDefaultKpiParams())
        coVerify(exactly = 1) {
            saleOrdersRepository.getSalesOrdersByCampaigns(any(), any())
            collectionRepository.getCollectionByCampaigns(any(), any())
            newCycleRepository.getNewCycleByCampaigns(any(), any())
            capitalizationRepository.getKpiDataByCampaignsAndUa(any(), any())
        }
        confirmVerified(
            saleOrdersRepository,
            collectionRepository,
            newCycleRepository,
            capitalizationRepository
        )
    }

    @Test
    fun `test to get Campaigns`() {
        val totalCampaigns = 2
        val campaigns = getCampaigns(LlaveUA(REGION, ZONE, SECTION, -1))
        val campaignsCodes = campaigns.map { it.codigo }
        campaigns shouldHaveSize totalCampaigns
        campaignsCodes.forEach { it shouldNotEqual EMPTY_STRING }
    }

    @Test
    fun `test to get Configuration`() = runBlockingTest {
        val currentConfiguration = Configuration(
            "CR", "Costa Rica", "¢",
            "+506", 0, 0, true,
            MainBrandType.ESIKA, false, false, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, Constant.NUMERO_UNO, false)
        val desiredConfiguration = getConfiguration(any())
        assertEquals(currentConfiguration.country, desiredConfiguration.country)
    }

    @Test
    fun `test to get current Person from Session`() {
        val currentRole = Rol.NINGUNO
        val person = BusinessPartner(
            1,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING
        )

        val desiredPerson = getCurrentPerson(currentRole, person)
        assertEquals(getSession().rol, desiredPerson?.role)
    }

    @Test
    fun `test to get Rol from Session`() {
        val currentRole = Rol.NINGUNO
        val desiredRole = getCurrentRole(currentRole)
        assertNotEquals(currentRole, desiredRole)
    }

    @Test
    fun `test to get Rol from User`() {
        val currentRole = Rol.SOCIA_EMPRESARIA
        val desiredRole = getCurrentRole(currentRole)
        assertEquals(currentRole, desiredRole)
    }

    @Test
    fun `test to get uaKey from Session`() {
        val ua: LlaveUA? = null
        val currentUa = getUaKey(ua)
        assertNotEquals(currentUa, ua)
    }

    @Test
    fun `test to get uaKey from User`() {
        val ua: LlaveUA? = LlaveUA(REGION, ZONE, SECTION, -1)
        val currentUa = getUaKey(ua)
        assertEquals(currentUa, ua)
    }

    private fun createUaKey() = LlaveUA(REGION, ZONE, SECTION, -1)

    private fun getUaKey(uaKey: LlaveUA?) = uaKey ?: getSession().llaveUA

    private fun getCurrentRole(role: Rol) =
        if (role == Rol.NINGUNO) getSession().rol else role

    private fun getCurrentPerson(role: Rol, person: Person?): Person? {
        return if (role != getSession().rol) person
        else person ?: getSession().person
    }

    private suspend fun getConfiguration(ua: LlaveUA) =
        configurationRepository.getConfiguration(ua)

    private fun createCampaigns() = listOf(
        Campania(
            codigo = "202003",
            periodo = Campania.Periodo.VENTA,
            orden = 1,
            inicioFacturacion = Date(),
            inicio = Date(),
            fin = Date(),
            esPrimerDiaFacturacion = true,
            nombreCorto = "C03"
        ),
        Campania(
            codigo = "202004",
            periodo = Campania.Periodo.VENTA,
            orden = 2,
            inicioFacturacion = Date(),
            inicio = Date(),
            fin = Date(),
            esPrimerDiaFacturacion = true,
            nombreCorto = "C04"
        )
    )

    private fun getSession() = requireNotNull(sessionRepository.getSession())

    private fun getCampaigns(uaKey: LlaveUA): List<Campania> {
        return campaignRepository.obtenerCampaniasSincrono(uaKey.formatHyphenIfNull())
            .take(KpiRules.MAXIMUM_CAMPAIGNS)
    }

    private fun createDefaultKpiParams() = KpiDashboardParams(
        role = Rol.GERENTE_ZONA,
        person = null,
        uaKey = null
    )

    private fun getCollectionMockData() =
        CollectionMockDataHelper.getCollectionEntities(createUaKey())

    companion object {
        private const val REGION = "09"
        private const val ZONE = "0906"
        private const val SECTION = "A"
    }
}
