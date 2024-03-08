package biz.belcorp.salesforce.modules.postulants.core.data.entities.google

import com.google.gson.annotations.SerializedName

class PredictionApiResponse {

    @SerializedName("predictions")
    val predictions: List<Prediction> = emptyList()

    class Prediction {
        @SerializedName("description")
        val description: String = ""
        @SerializedName("place_id")
        val placeId: String = ""
    }

}
