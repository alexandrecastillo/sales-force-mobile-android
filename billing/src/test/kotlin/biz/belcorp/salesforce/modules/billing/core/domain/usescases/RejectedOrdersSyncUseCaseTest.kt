@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.billing.core.domain.usescases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersSyncUseCase
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RejectedOrdersSyncUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val rejectedOrdersRepository = mockk<RejectedOrdersRepository>(relaxed = true)
    private val sessionRepository = mockk<SessionRepository>()
    private lateinit var rejectedOrdersSyncUseCase: RejectedOrdersSyncUseCase

    @Before
    fun setup() {
        every { sessionRepository.getSession() } returns mockk {
            every { countryIso } returns COUNTRY_ISO
            every { llaveUA } returns createUaKey()
            every { campaign } returns mockk {
                every { nombreCorto } returns CAMPAIGN_SHORT_NAME
            }
        }

        rejectedOrdersSyncUseCase = RejectedOrdersSyncUseCase(
            rejectedOrdersRepository,
            sessionRepository
        )
    }

    @Test
    fun `solving test for RejectedOrdersSyncUseCase`() = runBlockingTest {
        rejectedOrdersSyncUseCase.sync()
        coVerify(exactly = 1) { rejectedOrdersRepository.sync(any()) }
    }

    private fun createUaKey() = LlaveUA(REGION, ZONE, Constant.EMPTY_STRING, -1)

    companion object {
        private const val REGION = "06"
        private const val ZONE = "0620"
        private const val CAMPAIGN_SHORT_NAME = "C-18"
        private const val COUNTRY_ISO = "CO"
    }
}
