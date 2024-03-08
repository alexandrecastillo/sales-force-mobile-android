@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.kpis.core.domain.detailbutton

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.detailbutton.GetDetailButtonUseCase
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class GetDetailButtonUseCaseTest {

    private val campaignsRepository = mockk<CampaniasRepository>()
    private val sessionRepository = mockk<SessionRepository>()

    private lateinit var useCase: GetDetailButtonUseCase

    @Before
    fun setup() {
        useCase = GetDetailButtonUseCase(campaignsRepository, sessionRepository)
    }

    @Test
    fun `should be billing for detail button`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { sessionRepository.getSession()?.rol } returns Rol.GERENTE_REGION
        coEvery { campaignsRepository.obtenerCampaniaActualSuspend(uaKey) } returns NewCycleIndicatorMock.campaignMock
        coEvery { campaignsRepository.getPreviousCampaignSuspend(uaKey) } returns NewCycleIndicatorMock.campaignMock
        val isBilling = useCase.isBillingForDetailButton()
        isBilling.second shouldEqualTo true
    }

    @Test
    fun `shouldn't be billing for detail button`() = runBlockingTest {
        coEvery { sessionRepository.getSession()?.llaveUA } returns uaKey
        coEvery { sessionRepository.getSession()?.rol } returns Rol.GERENTE_REGION
        coEvery { campaignsRepository.obtenerCampaniaActualSuspend(uaKey) } returns NewCycleIndicatorMock.campaignSaleMock
        coEvery { campaignsRepository.getPreviousCampaignSuspend(uaKey) } returns NewCycleIndicatorMock.campaignSaleMock
        val isBilling = useCase.isBillingForDetailButton()
        isBilling.second shouldEqualTo false
    }
}
