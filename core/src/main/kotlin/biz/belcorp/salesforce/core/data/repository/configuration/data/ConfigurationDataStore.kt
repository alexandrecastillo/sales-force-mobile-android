package biz.belcorp.salesforce.core.data.repository.configuration.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationCountryEntity
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationZoneEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import io.objectbox.kotlin.boxFor

class ConfigurationDataStore {

    fun saveConfigurationCountry(config: ConfigurationCountryEntity) {
        boxStore.boxFor<ConfigurationCountryEntity>()
            .deleteAndInsert(listOf(config))
    }

    fun saveConfigurationZones(configZones: List<ConfigurationZoneEntity>) {
        boxStore.boxFor<ConfigurationZoneEntity>()
            .deleteAndInsert(configZones)
    }

    fun getConfiguration(uaKey: LlaveUA): ConfigurationCountryEntity {
        val configCountry = getConfigurationCountry() ?: return ConfigurationCountryEntity()
        val configZone = findConfigurationZone(configCountry.zones, uaKey)
        return configCountry.copy(isPdv = configZone?.isPdv ?: configCountry.isPdv)
    }

    fun getConfigurationCountry(): ConfigurationCountryEntity? {
        return boxStore.boxFor<ConfigurationCountryEntity>()
            .query()
            .build()
            .findFirst()
    }

    private fun findConfigurationZone(configZones: List<ConfigurationZoneEntity>, uaKey: LlaveUA)
        : ConfigurationZoneEntity? {
        return configZones.firstOrNull {
            it.region == uaKey.codigoRegion.orEmpty() && it.zone == uaKey.codigoZona.orEmpty()
        }
    }
}
