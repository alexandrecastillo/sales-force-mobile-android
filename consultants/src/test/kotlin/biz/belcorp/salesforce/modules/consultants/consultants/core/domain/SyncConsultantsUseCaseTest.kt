@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.consultants.consultants.core.domain

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SyncConsultantsUseCaseTest {

    private val sessionRepositoryMock = mockk<SessionRepository>(relaxed = true)
    private val consultantsRepository = mockk<ConsultantsSyncRepository>(relaxed = true)
    private val campaignRepositoryMock = mockk<CampaniasRepository>()

    private lateinit var syncUseCaseMock: SyncConsultantsUseCase

    @Before
    fun onBefore() {
        setupMocks()
        syncUseCaseMock = SyncConsultantsUseCase(
            consultantsRepository,
            campaignRepositoryMock,
            sessionRepositoryMock
        )
    }

    private fun setupMocks() {
        with(campaignRepositoryMock) {
            coEvery { obtenerCampaniaActualSuspend(any()) } returns mockk {
                every { codigo } returns "202003"
                every { periodo } returns Campania.Periodo.FACTURACION
                every { getPhase() } returns "V"
            }
        }
    }

    @Test
    fun `get consultants from cloud store success-test`() = runBlockingTest {
        val sessionUaKey = LlaveUA("01", "1112", "A", -1)
        prepareMocks(Rol.SOCIA_EMPRESARIA, sessionUaKey)
        syncUseCaseMock.sync()
    }

    private fun prepareMocks(role: Rol, uaKey: LlaveUA) {
        with(sessionRepositoryMock) {
            every { getSession() } returns mockk {
                every { rol } returns role
                every { llaveUA } returns uaKey
                every { region } returns uaKey.codigoRegion
                every { zona } returns uaKey.codigoZona
                every { seccion } returns uaKey.codigoSeccion
                every { countryIso } returns "PE"
            }
        }
        coEvery { consultantsRepository.sync(any()) } returns mockk {
            SyncType.FullyUpdated()
        }
    }

}
