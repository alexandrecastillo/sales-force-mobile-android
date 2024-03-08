package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta

import com.google.gson.annotations.SerializedName

class RutaApiResponse {

    @SerializedName("routes")
    val routes: List<Route> = emptyList()

    @SerializedName("status")
    val status: String = ""

    class Route {

        @SerializedName("legs")
        val legs: List<Leg> = emptyList()

        @SerializedName("overview_polyline")
        val polyline: Point? = null
    }

    class Leg {

        @SerializedName("duration")
        val duration = DurationDistance()

        @SerializedName("steps")
        val steps: List<Step> = emptyList()
    }

    class Step {

        @SerializedName("duration")
        val duration = DurationDistance()

        @SerializedName("polyline")
        val polyline = Polyline()
    }

    class DurationDistance {

        @SerializedName("text")
        val text: String? = null

        @SerializedName("value")
        val value: Int = 0
    }

    class Polyline {

        @SerializedName("points")
        val points: String = ""
    }

    class Point {

        @SerializedName("points")
        val points: String = ""
    }
}
