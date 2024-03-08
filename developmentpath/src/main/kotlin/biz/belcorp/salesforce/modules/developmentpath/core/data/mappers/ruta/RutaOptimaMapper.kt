package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.core.entities.sql.geo.RutaOptimaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.RutaApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta

class RutaOptimaMapper {

    fun parse(peticion: PeticionRutaOptima, respuesta: RespuestaRuta, id: Long): RutaOptimaEntity {
        val entidad = RutaOptimaEntity()
        entidad.id = id
        entidad.puntos = respuesta.recuperarPolyline()
        entidad.waypoints = peticion.waypoints
        entidad.origen = peticion.origen
        entidad.destino = peticion.destino

        return entidad
    }

    fun parse(entidad: RutaOptimaEntity?): List<RespuestaRuta.LatLon> {
        var lista = emptyList<RespuestaRuta.LatLon>()
        entidad?.let { lista = decodificarPolyline(it.puntos) }
        return lista
    }

    fun parseToRequest(ruta: PeticionRutaOptima): RutaApiRequest {
        return RutaApiRequest(
            origen = ruta.origen,
            destino = ruta.destino,
            optimize = ruta.optimizacion,
            modo = ruta.modo,
            wayPoints = ruta.waypoints)
    }

    private fun decodificarPolyline(polyline: String): List<RespuestaRuta.LatLon> {
        val puntos = ArrayList<RespuestaRuta.LatLon>()
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

            val p = RespuestaRuta.LatLon(lat.toDouble() / 1E5,
                lng.toDouble() / 1E5)
            puntos.add(p)
        }
        return puntos
    }
}
