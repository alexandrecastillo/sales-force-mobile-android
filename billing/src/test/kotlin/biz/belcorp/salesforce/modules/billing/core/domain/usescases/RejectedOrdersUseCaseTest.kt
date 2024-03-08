@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.billing.core.domain.usescases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RejectedOrdersUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val rejectedOrdersRepository = mockk<RejectedOrdersRepository>(relaxed = true)
    private val sessionRepository = mockk<SessionRepository>()
    private lateinit var rejectedOrdersUseCase: RejectedOrdersUseCase

    @Before
    fun setup() {
        every { sessionRepository.getSession() } returns mockk {
            every { countryIso } returns COUNTRY
            every { llaveUA } returns createUaKey()
            every { campaign } returns mockk {
                every { codigo } returns CAMPAIGN_CODE
            }
        }
        rejectedOrdersUseCase = RejectedOrdersUseCase(rejectedOrdersRepository, sessionRepository)
    }

    @Test
    fun `solving test for RejectedOrdersUseCase`() = runBlockingTest {
        rejectedOrdersUseCase.getRejectedOrders(LlaveUA())
        coVerify(exactly = 1) { rejectedOrdersRepository.getRejectedOrders(any(), any()) }
    }

    private fun createUaKey() = LlaveUA(REGION, ZONE, Constant.EMPTY_STRING, -1)

    companion object {
        private const val COUNTRY = "CO"
        private const val REGION = "06"
        private const val ZONE = "0620"
        private const val CAMPAIGN_CODE = "201918"
    }

}
