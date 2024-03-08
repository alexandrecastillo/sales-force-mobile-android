package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.google

import com.google.gson.annotations.SerializedName

class PredictionApiResponse {

    @SerializedName("predictions")
    val predictions: List<Prediction> = emptyList()

    class Prediction {
        @SerializedName("description")
        val description: String = ""
    }

}
