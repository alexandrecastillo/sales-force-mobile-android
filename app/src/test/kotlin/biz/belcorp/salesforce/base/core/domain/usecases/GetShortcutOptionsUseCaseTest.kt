@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.core.domain.usecases

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetShortcutOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test

class GetShortcutOptionsUseCaseTest {

    private val configRepository = mockk<ConfigurationRepository>()
    private val sessionRepository = mockk<SessionRepository>()
    private val optionsRepository = mockk<OptionsRepository>()
    private lateinit var configUseCase: ConfigurationUseCase
    private lateinit var shortcutUseCase: GetShortcutOptionsUseCase

    @Before
    fun setup() {
        configUseCase = ConfigurationUseCase(configRepository, sessionRepository)
        shortcutUseCase = GetShortcutOptionsUseCase(optionsRepository, configUseCase)
    }

    @Test
    fun `get shortcuts`() = runBlockingTest {
        coEvery { optionsRepository.getShortcutOptions() } returns dataMock()
        val options = shortcutUseCase.getOptions()
        options shouldHaveSize 3
    }

    @Test
    fun `get shortcuts with exclude bright path unless`() = runBlockingTest {
        val shortcuts = dataMock().toMutableList().apply {
            add(Option(OptionsIdentifier.BRIGHT_PATH.toLong(), "Camino Brillante", 4, ""))
        }
        coEvery { configUseCase.getConfiguration() } returns dataConfigPdvMock()
        coEvery { optionsRepository.getShortcutOptions() } returns shortcuts
        val options = shortcutUseCase.getOptions()
        options shouldHaveSize 4
    }

    private fun dataConfigPdvMock(): Configuration {
        return Configuration(
            "PE",
            "Peru",
            "S/.",
            "+51",
            0L,
            0L,
            true,
            MainBrandType.ESIKA,
            false,
            false,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            Constant.NUMERO_UNO,
            false
        )
    }

    private fun dataMock(): List<Option> {
        return listOf(
            Option(OptionsIdentifier.CAMPAIGN_REPORTS.toLong(), "Reporte de Campania", 1, ""),
            Option(OptionsIdentifier.JOIN_US.toLong(), "Unete", 2, ""),
            Option(OptionsIdentifier.DEVELOPMENT_PATH.toLong(), "Ruta de Desarrollo", 3, "")
        )
    }
}
