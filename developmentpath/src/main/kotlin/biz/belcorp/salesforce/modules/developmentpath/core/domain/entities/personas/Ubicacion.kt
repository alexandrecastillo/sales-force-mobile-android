package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import java.io.Serializable


class Ubicacion(
    val direccion: String?,
    var latitud: Double?,
    var longitud: Double?
) : Serializable {

    companion object {
        fun construirDummy() =
            Ubicacion(
                null,
                null,
                null
            )
    }

    val poseeCoordenadas get() = latitud != null && longitud != null

    fun distanciaA(punto: Pair<Double, Double>): Double? {
        return if (latitud != null && longitud != null) {
            distanciaEnMetros(Pair(latitud!!, longitud!!), punto)
        } else null
    }

    private fun distanciaEnMetros(
        puntoOrigen: Pair<Double, Double>,
        puntoLlegada: Pair<Double, Double>
    ): Double {
        val radioTierra = 6371000.0f
        val dLat = Math.toRadians(puntoOrigen.first - puntoLlegada.first)
        val dLng = Math.toRadians(puntoOrigen.second - puntoLlegada.second)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(puntoOrigen.first)) *
            Math.cos(Math.toRadians(puntoLlegada.first)) *
            Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return (radioTierra * c)
    }
}

