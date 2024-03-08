@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.core.domain.saleorders

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.SalesOrdersMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.campaignMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.lastCampaignMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.uaKeyMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class SaleOrdersUseCaseTest {

    private val saleOrdersRepository = mockk<SaleOrdersRepository>()
    private val campaignsRepository = mockk<CampaniasRepository>()
    private val sessionRepository = mockk<SessionRepository>()
    private val configurationRepository = mockk<ConfigurationRepository>()
    private val businessPartnerRepository = mockk<BusinessPartnerRepository>()
    private val person = mockk<Person>()
    private val campaign = mockk<Campania>()

    private lateinit var useCase: SaleOrdersUseCase

    private val salesOrdersMapper = SaleOrdersMapper()

    @Before
    fun setup() {
        coEvery { configurationRepository.getConfiguration(any()) } returns createConfiguration()
        every { sessionRepository.getSession() } returns createSession()
        every { campaignsRepository.obtenerCampaniaActual(any()) } returns campaignMock
        every { campaignsRepository.obtenerCampaniaInmediataAnterior(any()) } returns lastCampaignMock
        coEvery { businessPartnerRepository.getBusinessPartner(any<LlaveUA>()) } returns createBusinessPartner()

        useCase =
            SaleOrdersUseCase(
                saleOrdersRepository,
                campaignsRepository,
                sessionRepository,
                configurationRepository,
                businessPartnerRepository
            )
    }

    @Test
    fun `should find a saleorders indicator`() = runBlockingTest {
        val mockupData = SalesOrdersMockDataHelper.getSalesOrdersEntities(
            uaKeyMock, campaignMock.codigo, salesOrdersMapper
        )
        coEvery {
            saleOrdersRepository.getSalesOrdersByCampaigns(any(), any())
        } returns mockupData
        useCase.getSaleOrder(uaKeyMock)
        coVerify { saleOrdersRepository.getSalesOrdersByCampaigns(any(), any()) }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a saleorders indicator`() = runBlockingTest {
        coEvery { saleOrdersRepository.getSalesOrdersByCampaigns(any(), any()) } throws Exception()
        useCase.getSaleOrder(uaKeyMock)
        coVerify { saleOrdersRepository.getSalesOrdersByCampaigns(any(), any()) }
    }

    private fun createSession(): Sesion {
        return Sesion(
            uaKeyMock.codigoRegion,
            uaKeyMock.codigoZona,
            "PE",
            uaKeyMock.codigoSeccion,
            Rol.SOCIA_EMPRESARIA.codigoRol,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            person,
            campaign
        )
    }

    private fun createConfiguration() = Configuration(
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING,
        0,
        0,
        false,
        MainBrandType.ESIKA,
        false,
        false,
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING,
        Constant.NUMERO_UNO,
        false
    )

    private fun createBusinessPartner() = BusinessPartner(
        id = Random.nextLong(),
        code = EMPTY_STRING,
        secondSurname = EMPTY_STRING,
        secondName = EMPTY_STRING,
        firstName = EMPTY_STRING,
        document = EMPTY_STRING,
        firstSurname = EMPTY_STRING,
        birthDate = EMPTY_STRING,
        anniversaryDate = EMPTY_STRING,
        leaderClassification = EMPTY_STRING,
        level = "PLATINUM",
        levelCode = EMPTY_STRING,
        status = EMPTY_STRING
    ).apply {
        uaKey = LlaveUA("09", "0908", "A", NEGATIVE_NUMBER_ONE.toLong())
    }
}
