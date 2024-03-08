package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaViewModel

interface MapaBaseView {

    fun showRoute(puntos: List<RespuestaRuta.LatLon>)

    fun mostrarMensaje(mensaje: String)

    fun mostrarProgress(mensaje: String)

    fun ocultarProgress()

    fun cargar(personas: PersonaEnMapaViewModel)

    fun mostrarDialogSinConexion()

    fun mostrarProgressPerfil()

    fun ocultarProgressPerfil()

    fun irAPerfil(personIdentifier: PersonIdentifier)

    fun mostrarErrorConexionPerfil(rol: Rol)
}
