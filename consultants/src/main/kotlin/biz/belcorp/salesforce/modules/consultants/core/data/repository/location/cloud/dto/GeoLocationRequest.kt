package biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud.dto

import kotlinx.serialization.SerialName

class GeoLocationRequest {

    @SerialName("Codigo")
    var codigo: String? = null

    @SerialName("Latitud")
    var latitude: String? = null

    @SerialName("Longitud")
    var longitude: String? = null

}
