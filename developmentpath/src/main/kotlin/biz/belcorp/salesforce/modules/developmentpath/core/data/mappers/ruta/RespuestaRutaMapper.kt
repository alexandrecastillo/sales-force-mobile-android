package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.RutaApiResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta

class RespuestaRutaMapper {

    fun parseToRespuesta(response: RutaApiResponse): RespuestaRuta {
        return RespuestaRuta(
            routes = parseListRoute(response.routes),
            status = response.status)
    }

    private fun parseRoute(response: RutaApiResponse.Route): RespuestaRuta.Route {
        return RespuestaRuta.Route(
            legs = parseListLeg(response.legs),
            overviewPolyline = parseToPolyline(response.polyline))
    }

    private fun parseListRoute(lista: List<RutaApiResponse.Route>): List<RespuestaRuta.Route> {
        val routes = ArrayList<RespuestaRuta.Route>()
        lista.forEach {
            routes.add(parseRoute(it))
        }
        return routes
    }

    private fun parseLeg(response: RutaApiResponse.Leg): RespuestaRuta.Leg {
        return RespuestaRuta.Leg(
            duration = parseDistanceDuration(response.duration),
            steps = parseListStep(response.steps)
        )
    }

    private fun parseListLeg(lista: List<RutaApiResponse.Leg>): List<RespuestaRuta.Leg> {
        val legs = ArrayList<RespuestaRuta.Leg>()
        lista.forEach {
            legs.add(parseLeg(it))
        }
        return legs
    }

    private fun parseDistanceDuration(response: RutaApiResponse.DurationDistance):
        RespuestaRuta.DurationDistance {
        return RespuestaRuta.DurationDistance(
            text = response.text,
            value = response.value
        )
    }

    private fun parseStep(response: RutaApiResponse.Step): RespuestaRuta.Step {
        return RespuestaRuta.Step(
            duration = parseDistanceDuration(response.duration),
            polyline = parsePolyline(response.polyline)
        )
    }

    private fun parseListStep(lista: List<RutaApiResponse.Step>): List<RespuestaRuta.Step> {
        val steps = ArrayList<RespuestaRuta.Step>()
        lista.forEach {
            steps.add(parseStep(it))
        }
        return steps
    }

    private fun parsePolyline(response: RutaApiResponse.Polyline): RespuestaRuta.Polyline {
        return RespuestaRuta.Polyline(
            points = response.points
        )
    }

    private fun parseToPolyline(responseApi: RutaApiResponse.Point?) = responseApi?.points ?: ""
}
