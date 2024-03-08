package biz.belcorp.salesforce.modules.consultants.core.domain.repository.location

interface GeoLocationRepository {

    suspend fun saveGeoLocation(consultantCode: String, latitude: String, longitude: String)

}
