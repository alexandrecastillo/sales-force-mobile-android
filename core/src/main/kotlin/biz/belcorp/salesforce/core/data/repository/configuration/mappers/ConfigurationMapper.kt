package biz.belcorp.salesforce.core.data.repository.configuration.mappers

import biz.belcorp.salesforce.core.data.repository.configuration.cloud.dto.ConfigurationQuery
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationCountryEntity
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationZoneEntity

class ConfigurationMapper {

    fun map(data: ConfigurationQuery.Data): Pair<ConfigurationCountryEntity, List<ConfigurationZoneEntity>> {
        val configCountry = ConfigurationCountryEntity()
            .apply {
                country = data.configuration.country
                isPdv = data.configuration.isPdv
                countryName = data.configuration.countryName
                currencySymbol = data.configuration.currencySymbol
                phoneCode = data.configuration.phoneCode
                minimalOrderAmount = data.configuration.minimalOrderAmount
                tippingPoint = data.configuration.tippingPoint
                flagShowProactive = data.configuration.flagShowProactive
                isGanaMas = data.configuration.isGanaMas
                mainBrand = data.configuration.mainBrand
                flagMto = data.configuration.flagMto
                flagShowProactive = data.configuration.flagShowProactive
            }
        val configZones =
            data.configuration.zonesPdv.map { map(configCountry, it) }

        return Pair(configCountry, configZones)
    }

    private fun map(
        configCountry: ConfigurationCountryEntity,
        configZone: ConfigurationQuery.Data.Configuration.ZonePdv
    ): ConfigurationZoneEntity {
        return ConfigurationZoneEntity(
            region = configZone.regionCode,
            zone = configZone.zoneCode,
            isPdv = configZone.isPdv,
            isDataReport = configZone.isDataReport
        ).apply {
            configParent.target = configCountry
        }
    }

    fun map(
        configCountry: ConfigurationCountryEntity,
        isOnlineStoreEnabled: Boolean,
        onlineStoreTitle: String,
        onlineStoreType: String,
        updateType: String
    ): Configuration {
        return Configuration(
            country = configCountry.country,
            isPdv = configCountry.isPdv,
            countryName = configCountry.countryName,
            currencySymbol = configCountry.currencySymbol,
            phoneCode = configCountry.phoneCode,
            minimalOrderAmount = configCountry.minimalOrderAmount,
            tippingPoint = configCountry.tippingPoint,
            mainBrand = configCountry.mainBrand,
            isGanaMas = configCountry.isGanaMas,
            flagMto = configCountry.flagMto,
            flagShowProactive = configCountry.flagShowProactive,
            isOnlineStoreEnabled = isOnlineStoreEnabled,
            onlineStoreTitle = onlineStoreTitle,
            onlineStoreType = onlineStoreType,
            updateType = updateType
        )
    }
}
