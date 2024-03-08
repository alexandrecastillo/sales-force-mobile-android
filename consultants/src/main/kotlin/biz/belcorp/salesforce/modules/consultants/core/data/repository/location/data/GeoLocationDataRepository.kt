package biz.belcorp.salesforce.modules.consultants.core.data.repository.location.data

import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud.GeoLocationCloudDataStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.location.GeoLocationRepository

class GeoLocationDataRepository(
    private val cloudStore: GeoLocationCloudDataStore,
    private val dataStore: GeoLocationDataStore
) : GeoLocationRepository {

    override suspend fun saveGeoLocation(
        consultantCode: String,
        latitude: String,
        longitude: String
    ) {
        dataStore.saveGeoLocation(consultantCode, latitude, longitude)
        saveOnCloudGeoLocation(consultantCode, latitude, longitude)
    }

    private suspend fun saveOnCloudGeoLocation(
        consultantCode: String,
        latitude: String,
        longitude: String
    ) {
        cloudStore.saveGeoLocation(consultantCode, latitude, longitude)
    }
}
