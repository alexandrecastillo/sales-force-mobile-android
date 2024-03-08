package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.pow
import kotlin.math.sqrt

class ListaMarcadores(private var puntos: MutableList<Marcador>) {

    fun eliminarEscalasYCercanas() = puntos.retainAll { it.esUbicacion }

    fun insertar(marcador: Marcador) {
        var elemento: Marcador? = null
        if (marcador.esUbicacion) {
            elemento = puntos.find { it.esUbicacion }
        }
        elemento?.let { puntos.remove(it) }
        puntos.add(marcador)
    }

    fun size() = puntos.size

    fun convertirABoundsBuilder(): LatLngBounds.Builder {
        val bounds = LatLngBounds.builder()
        puntos.forEach {
            bounds.include(it.latLng)
        }
        return bounds
    }

    fun recuperarUbicacion() = puntos.find { it.esUbicacion }

    private fun recuperarEscalas(): ListaMarcadores {
        return ListaMarcadores(puntos
                .filter { !it.esUbicacion && it.esParteDeRuta } as MutableList<Marcador>)
    }

    val sonSuficientes
        get(): Boolean {
            return (recuperarUbicacion() != null) && recuperarEscalas().size() > 0
        }

    fun eliminarCercanas() {
        puntos = puntos.filter { !it.estaCerca } as MutableList<Marcador>
    }

    fun hallarPuntoMasLejano(): Marcador? {
        val ubicacion = recuperarUbicacion()
        var puntoLejano: Marcador? = null
        if (ubicacion != null) {
            var distanciaMaxima = 0.0
            recuperarEscalas().puntos.forEach {
                if (calcularDistancia(it.latLng, ubicacion.latLng) > distanciaMaxima) {
                    distanciaMaxima = calcularDistancia(it.latLng, ubicacion.latLng)
                    puntoLejano = it
                }
            }
        }
        return puntoLejano
    }

    private fun calcularDistancia(puntoA: LatLng, puntoB: LatLng): Double {
        return sqrt(
                (puntoA.longitude - puntoB.longitude).pow(2) +
                        (puntoA.latitude - puntoB.latitude).pow(2))
    }

    fun parsearEscalas(): String {
        var waypoints = ""
        val escalas = recuperarEscalas().puntos
        escalas.forEach {
            waypoints += it.toString() + "|"
        }
        if (escalas.size > 0) {
            waypoints = waypoints.substring(0, waypoints.length - 1)
        }
        return waypoints
    }
}
