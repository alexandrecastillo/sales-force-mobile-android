package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.location

import biz.belcorp.salesforce.modules.consultants.core.domain.repository.location.GeoLocationRepository
import com.google.android.gms.maps.model.LatLng

class GeoLocationUseCase(
    private val repository: GeoLocationRepository
) {

    suspend fun saveGeoLocation(consultantId: String, latLng: LatLng) {
        repository.saveGeoLocation(
            consultantId,
            latLng.latitude.toString(),
            latLng.longitude.toString()
        )
    }
}
