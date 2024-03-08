package biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud

import biz.belcorp.salesforce.modules.consultants.core.data.network.GeoLocationApi
import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud.dto.GeoLocationRequest

class GeoLocationCloudDataStore(private val api: GeoLocationApi) {

    suspend fun saveGeoLocation(consultantCode: String, latitude: String, longitude: String) {
        val request = GeoLocationRequest().apply {
            this.codigo = consultantCode
            this.latitude = latitude
            this.longitude = longitude
        }
        api.saveGeoLocation(request)
    }

}
