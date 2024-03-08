package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class ManageTopicsUseCaseTest {

    private var sessionRepoMock = mockk<SessionRepository>()
    private var messagingRepoMock = mockk<CloudMessagingRepository>(relaxed = true)

    private lateinit var useCase: ManageTopicsUseCase

    @Before
    fun setup() {
        useCase = ManageTopicsUseCase(sessionRepoMock, messagingRepoMock)
    }


    @Test
    fun `test subscribe topics DV`() {

        mockDV()

        useCase.subscribeTopics()

        verify { messagingRepoMock.subscribeTopics(topics) }
        verify { messagingRepoMock.subscribeTopicsWithEnv(topicsWithEnvDV) }

    }

    @Test
    fun `test unsubscribe topics DV`() {

        mockDV()

        useCase.unsubscribeTopics()

        verify { messagingRepoMock.unsubscribeTopics(topics) }
        verify { messagingRepoMock.unsubscribeTopicsWithEnv(topicsWithEnvDV) }

    }

    @Test
    fun `test subscribe topics GR`() {

        mockGR()

        useCase.subscribeTopics()

        verify { messagingRepoMock.subscribeTopics(topics) }
        verify { messagingRepoMock.subscribeTopicsWithEnv(topicsWithEnvGR) }

    }

    @Test
    fun `test unsubscribe topics GR`() {

        mockGR()

        useCase.unsubscribeTopics()

        verify { messagingRepoMock.unsubscribeTopics(topics) }
        verify { messagingRepoMock.unsubscribeTopicsWithEnv(topicsWithEnvGR) }

    }

    @Test
    fun `test subscribe topics GZ`() {

        mockGZ()

        useCase.subscribeTopics()

        verify { messagingRepoMock.subscribeTopics(topics) }
        verify { messagingRepoMock.subscribeTopicsWithEnv(topicsWithEnvGZ) }

    }

    @Test
    fun `test unsubscribe topics GZ`() {

        mockGZ()

        useCase.unsubscribeTopics()

        verify { messagingRepoMock.unsubscribeTopics(topics) }
        verify { messagingRepoMock.unsubscribeTopicsWithEnv(topicsWithEnvGZ) }

    }

    @Test
    fun `test subscribe topics SE`() {

        mockSE()

        useCase.subscribeTopics()

        verify { messagingRepoMock.subscribeTopics(topics) }
        verify { messagingRepoMock.subscribeTopicsWithEnv(topicsWithEnvSE) }

    }

    @Test
    fun `test unsubscribe topics SE`() {

        mockSE()

        useCase.unsubscribeTopics()

        verify { messagingRepoMock.unsubscribeTopics(topics) }
        verify { messagingRepoMock.unsubscribeTopicsWithEnv(topicsWithEnvSE) }

    }

    private fun mockDV() {
        every { sessionRepoMock.getSession() } returns mockk {
            every { rol } returns Rol.DIRECTOR_VENTAS
            every { countryIso } returns "PE"
        }
    }

    private fun mockGR() {
        every { sessionRepoMock.getSession() } returns mockk {
            every { rol } returns Rol.GERENTE_REGION
            every { countryIso } returns "PE"
            every { region } returns "01"
        }
    }

    private fun mockGZ() {
        every { sessionRepoMock.getSession() } returns mockk {
            every { rol } returns Rol.GERENTE_ZONA
            every { countryIso } returns "PE"
            every { region } returns "01"
            every { zona } returns "0110"
        }
    }

    private fun mockSE() {
        every { sessionRepoMock.getSession() } returns mockk {
            every { rol } returns Rol.SOCIA_EMPRESARIA
            every { countryIso } returns "PE"
            every { region } returns "01"
            every { zona } returns "0110"
            every { seccion } returns "A"
        }
    }

    private val topics
        get() = listOf(
            "PUSH_RC"
        )

    private val topicsWithEnvDV
        get() = listOf(
            "ZONIFICATION_PE",
            "LEADER_PE"
        )

    private val topicsWithEnvGR
        get() = listOf(
            "ZONIFICATION_PE",
            "ZONIFICATION_PE_01",
            "LEADER_PE_01"
        )

    private val topicsWithEnvGZ = listOf(
        "ZONIFICATION_PE",
        "ZONIFICATION_PE_01",
        "ZONIFICATION_PE_01_0110",
        "LEADER_PE_01_0110"
    )

    private val topicsWithEnvSE = listOf(
        "ZONIFICATION_PE",
        "ZONIFICATION_PE_01",
        "ZONIFICATION_PE_01_0110",
        "ZONIFICATION_PE_01_0110_A",
        "LEADER_PE_01_0110_A"
    )

}
