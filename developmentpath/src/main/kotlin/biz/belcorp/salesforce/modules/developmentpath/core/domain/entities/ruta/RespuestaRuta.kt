package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

class RespuestaRuta(
    val routes: List<Route> = emptyList(),
    val status: String? = null
) {

    fun recuperarPuntos(): List<LatLon> {
        val puntos = ArrayList<LatLon>()
        if (routes.isNotEmpty()) {
            routes[0].legs.forEachIndexed { index, leg ->
                if (index != routes[0].legs.size - 1) {
                    leg.steps.forEach { step ->
                        decodificarPolyline(step.polyline.points).forEach {
                            puntos.add(it)
                        }
                    }
                }
            }
        }
        return puntos
    }

    fun recuperarPolyline(): String {
        var polyline = ""
        routes.forEach {
            polyline += polyline + it.overviewPolyline
        }
        return polyline
    }

    private fun decodificarPolyline(polyline: String): List<LatLon> {
        val puntos = ArrayList<LatLon>()
        var index = 0
        val len = polyline.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = polyline.codePointAt(index++) - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = polyline.codePointAt(index++) - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLon(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            puntos.add(p)
        }
        return puntos
    }

    class Route(
        val legs: List<Leg> = emptyList(),
        val overviewPolyline: String
    )

    class Leg(
        val duration: DurationDistance,
        val steps: List<Step> = emptyList()
    )

    class Step(
        val duration: DurationDistance,
        val polyline: Polyline
    )

    class DurationDistance(
        val text: String? = null,
        val value: Int = 0
    )

    class LatLon(
        val latitud: Double = 0.0,
        val longitud: Double = 0.0,
        val esID: Boolean = false
    )

    class Polyline(val points: String)
}
