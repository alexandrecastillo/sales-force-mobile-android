@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class ConfigurationUseCaseTest {

    private val configRepository = mockk<ConfigurationRepository>()
    private val sessionRepository = mockk<SessionRepository>()
    private lateinit var configUseCase: ConfigurationUseCase
    private val uaKey = mockk<LlaveUA>()

    @Before
    fun setup() {
        configUseCase = ConfigurationUseCase(configRepository, sessionRepository)
    }

    @Test
    fun `test get configuration no pdv`() = runBlockingTest {
        coEvery { configRepository.getConfiguration(uaKey) } returns createConfigData()
        configUseCase.getConfiguration(uaKey).isPdv shouldEqual false
    }

    @Test
    fun `test get configuration pdv`() = runBlockingTest {
        coEvery { configRepository.getConfiguration(uaKey) } returns createConfigPdvData()

        configUseCase.getConfiguration(uaKey).isPdv shouldEqual true
    }

    private fun createConfigData(): Configuration {
        return Configuration(
            "PE",
            "Peru",
            "S/.",
            "+51",
            1000,
            2000,
            false,
            MainBrandType.ESIKA,
            false,
            false,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.NUMERO_UNO,
            false
        )
    }

    private fun createConfigPdvData(): Configuration {
        return Configuration(
            "PE",
            "Peru",
            "S/.",
            "+51",
            1000,
            2000,
            true,
            MainBrandType.LBEL,
            true,
            false,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.NUMERO_UNO,
            false
        )
    }
}
