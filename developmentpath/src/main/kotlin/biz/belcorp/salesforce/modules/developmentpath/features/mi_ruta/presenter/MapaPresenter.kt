package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RecuperarRutaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MapaBaseView

class MapaPresenter(
    private val view: MapaBaseView,
    private val recuperarRutaUseCase: RecuperarRutaUseCase
) {

    var rol: Rol = Rol.NINGUNO

    fun solicitarRuta(puntos: ListaMarcadores) {
        if (puntos.sonSuficientes) {
            view.mostrarProgress("Hallando ruta óptima...")
            val peticion = armarPeticionRuta(puntos)
            recuperarRutaUseCase.recuperarRuta(peticion, RecuperarRutaSubscriber())
        } else {
            view.ocultarProgress()
            mostrarMensajeError("Insuficientes puntos para pintar ruta.")
        }
    }

    private inner class RecuperarRutaSubscriber : BaseSingleObserver<List<RespuestaRuta.LatLon>>() {

        override fun onError(e: Throwable) {
            view.ocultarProgress()
            if (e.message == "Sin conexion") {
                view.mostrarDialogSinConexion()
            }
        }

        override fun onSuccess(t: List<RespuestaRuta.LatLon>) {
            val puntos = t.filter { !it.esID }
            view.showRoute(puntos)
            view.ocultarProgress()
        }
    }

    private fun mostrarMensajeError(mensaje: String) = view.mostrarMensaje(mensaje)

    private fun armarPeticionRuta(puntos: ListaMarcadores): PeticionRutaOptima {
        val origen = puntos.recuperarUbicacion().toString()
        val destino = (puntos.hallarPuntoMasLejano() ?: origen).toString()
        val paradas = puntos.parsearEscalas()
        return PeticionRutaOptima(
            origen = origen,
            destino = destino,
            modo = "walking",
            waypoints = paradas,
            rol = rol
        )
    }

    fun devolverTextoPorRol() = when (rol) {
        Rol.GERENTE_ZONA -> "socias"
        Rol.SOCIA_EMPRESARIA -> "consultoras"
        else -> ""
    }

    fun destroy() {
        recuperarRutaUseCase.dispose()
    }
}
