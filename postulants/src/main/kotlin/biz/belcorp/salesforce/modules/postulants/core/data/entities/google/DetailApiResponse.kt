package biz.belcorp.salesforce.modules.postulants.core.data.entities.google

import com.google.gson.annotations.SerializedName

class DetailApiResponse {

    @SerializedName("result")
    val result: Result? = null

    class Result {
        @SerializedName("geometry")
        val geometry: Geometry? = null
        @SerializedName("place_id")
        val placeId: String = ""

        class Geometry {
            @SerializedName("location")
            val location: Location? = null

            class Location {
                @SerializedName("lat")
                val latitud: Double? = null
                @SerializedName("lng")
                val longitud: Double? = null
            }
        }
    }

}
