package biz.belcorp.salesforce.core.data.repository.configuration

import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.ConfigurationCloudStore
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto.ConfigurationQuery
import biz.belcorp.salesforce.core.data.repository.configuration.data.ConfigurationDataStore
import biz.belcorp.salesforce.core.data.repository.configuration.mappers.ConfigurationMapper
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository

class ConfigurationDataRepository(
    private val featuresPreferences: FeaturesSharedPreferences,
    private val configPreferences: ConfigSharedPreferences,
    private val configCloudStore: ConfigurationCloudStore,
    private val configDataStore: ConfigurationDataStore,
    private val configMapper: ConfigurationMapper
) : ConfigurationRepository {

    override suspend fun sync(country: String) {
        val query = ConfigurationQuery(country)
        val data = configCloudStore.getConfiguration(query)
        val (configCountry, configZones) = configMapper.map(data)
        configDataStore.saveConfigurationCountry(configCountry)
        configDataStore.saveConfigurationZones(configZones)
    }

    override suspend fun getConfiguration(uaKey: LlaveUA?): Configuration {
        val config = if (uaKey != null) {
            configDataStore.getConfiguration(uaKey)
        } else {
            requireNotNull(configDataStore.getConfigurationCountry())
        }
        return configMapper.map(
            config,
            featuresPreferences.onlineStoreEnabled,
            featuresPreferences.onlineStoreTitle,
            featuresPreferences.onlineStoreType,
            configPreferences.updateType.orEmpty()
        )
    }
}
