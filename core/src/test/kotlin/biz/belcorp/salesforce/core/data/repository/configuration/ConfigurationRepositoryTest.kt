@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.configuration

import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.ConfigurationCloudStore
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto.ConfigurationQuery
import biz.belcorp.salesforce.core.data.repository.configuration.data.ConfigurationDataStore
import biz.belcorp.salesforce.core.data.repository.configuration.mappers.ConfigurationMapper
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ConfigurationRepositoryTest {

    private val featuresPreferencesMock = mockk<FeaturesSharedPreferences>(relaxed = true)
    private val configPreferencesMock = mockk<ConfigSharedPreferences>(relaxed = true)
    private val cloudStoreMock = mockk<ConfigurationCloudStore>(relaxed = true)
    private val dataStoreMock = mockk<ConfigurationDataStore>(relaxed = true)
    private val mapper = ConfigurationMapper()

    private lateinit var repository: ConfigurationRepository

    @Before
    fun before() {
        repository =
            ConfigurationDataRepository(
                featuresPreferencesMock,
                configPreferencesMock,
                cloudStoreMock,
                dataStoreMock,
                mapper
            )
    }

    @Test
    fun `test sync configuration`() = runBlockingTest {
        coEvery { cloudStoreMock.getConfiguration(any()) } returns createConfiguration()

        repository.sync("PE")

        verify(exactly = 1) {
            dataStoreMock.saveConfigurationCountry(any())
            dataStoreMock.saveConfigurationZones(any())
        }
    }

    private fun createConfiguration(): ConfigurationQuery.Data {
        return ConfigurationQuery.Data(
            ConfigurationQuery.Data.Configuration(
                "PE",
                "Peru",
                "S/.",
                "+51",
                1000,
                20000,
                false,
                "false",
                false,
                MainBrandType.ESIKA,
                1,
                emptyList()
            )
        )
    }
}
