package biz.belcorp.salesforce.modules.postulants.core.data.repository.maps

import android.util.Log
import biz.belcorp.salesforce.modules.postulants.core.data.entities.google.DetailApiResponse
import biz.belcorp.salesforce.modules.postulants.core.data.entities.google.PredictionApiResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import com.google.gson.JsonObject

class GoogleMapper {

    fun map(prediction: PredictionApiResponse): List<Place> {
        return prediction.predictions.map {
            Place(it.placeId, it.description)
        }
    }

    fun map(detail: DetailApiResponse): DetailPlace? {
        Log.i("GoogleMapper", "detail:lat:${detail.result?.geometry?.location?.latitud},lon:${detail.result?.geometry?.location?.longitud}")
        return detail.result?.let {
            Log.i("GoogleMapper", "result")
            val location = it.geometry?.location ?: return null
            Log.i("GoogleMapper", "location")
            DetailPlace(it.placeId, location.latitud, location.longitud)
        }

    }

    fun map(json: JsonObject): DetailPlace? {
        return try {
            Log.i("GoogleMapper", "map:${json.toString()}")

            val result = json.getAsJsonObject("result")
            val placeId = result.getAsJsonPrimitive("place_id")

            val geometry = result.getAsJsonObject("geometry")
            val location = geometry.getAsJsonObject("location")

            val latitude = location.getAsJsonPrimitive("lat")
            val longitude = location.getAsJsonPrimitive("lng")

            DetailPlace(placeId.asString, latitude.asDouble, longitude.asDouble)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("GoogleMapper", "e:${e.message}")
            null
        }

    }

}
