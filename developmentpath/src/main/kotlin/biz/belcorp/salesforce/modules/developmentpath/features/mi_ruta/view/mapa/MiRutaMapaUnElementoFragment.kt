package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.core.utils.showSnackBar
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.Marcador
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.MapaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaInformacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilBuilder
import biz.belcorp.salesforce.modules.developmentpath.utils.drawRoute
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_mi_ruta_mapa_un_elemento.*

class MiRutaMapaUnElementoFragment : MapaBaseFragment(),
    MapaBaseFragment.EventosMapa,
    MapaBaseView {

    override fun getLayout(): Int {
        listener = this
        return R.layout.fragment_mi_ruta_mapa_un_elemento
    }

    private val presenter: MapaPresenter by injectFragment()

    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()
    private var marcadores = ListaMarcadores(ArrayList())
    lateinit var persona: PersonaEnMapaModel
    lateinit var acciones: ListenerAccionesEnMapa
    private var puntosRuta: List<RespuestaRuta.LatLon> = emptyList()

    companion object {

        private const val ROL = "ROL"
        private lateinit var rol: Rol

        fun newInstance(rol: Rol): MiRutaMapaUnElementoFragment {
            val args = Bundle()
            val fragment = MiRutaMapaUnElementoFragment()
            args.putSerializable(ROL, rol)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rol = arguments!!.getSerializable(ROL) as Rol
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        obtenerDimensiones(view)
        mapView = map_view_mi_ruta_un_elemento
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        presenter.rol = rol
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun alCargarMapa() = agregarMarcador()

    override fun alRechazarActivarGPS() = mostrarMensaje("Es necesario activar el GPS.")

    override fun alClickearMarcador(marcador: Marker) {
        val persona = marcador.tag
        if (persona is PersonaEnMapaModel) {
            val personIdentifier = PersonIdentifier(persona.personaId, persona.personCode, persona.rol)
            PerfilBuilder
                .conParametros(personIdentifier)
                .redirigirAAcompaniamiento()
                .recuperarFragment()
                .showOnce(childFragmentManager)
        }
    }

    override fun alRecuperarUbicacion() = guardarUbicacion()

    override fun alEsperarUbicacion() = mostrarProgress("Recuperando ubicación...")

    private fun agregarMarcador() {
        if (!isAttached()) return
        val suministrador = inflarLayoutsMarkers()
        with(persona) {
            val latLng = LatLng(persona.ubicacion.latitud!!, persona.ubicacion.longitud!!)
            val marker = map?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(suministrador.recuperarImagen(this))
            )
            marker?.tag = this
            marcadores.eliminarEscalasYCercanas()
            marcadores.insertar(
                Marcador(
                    latLng = latLng,
                    esUbicacion = false,
                    esParteDeRuta = true
                )
            )
        }
        enfocarMapa(marcadores)
    }

    private fun guardarUbicacion() {
        if (ubicacion != null) {
            val latLng = LatLng(ubicacion!!.latitude, ubicacion!!.longitude)
            marcadores.insertar(Marcador(latLng = latLng, esUbicacion = true, id = 0))
            solicitarRuta()
        } else {
            mostrarMensaje("No se pudo get la ubicación.")
            ocultarProgress()
        }
        enfocarMapa(marcadores)
    }

    private fun solicitarRuta() = presenter.solicitarRuta(marcadores)

    override fun showRoute(puntos: List<RespuestaRuta.LatLon>) {
        context?.apply {
            puntosRuta = puntos
            map?.drawRoute(puntosRuta, this)
        }
    }

    override fun cargar(personas: PersonaEnMapaViewModel) {
        if (context == null) return
        persona = personas.planificadas.find { it.personaId == persona.personaId } ?: persona
        map?.clear()
        agregarMarcador()
        showRoute(puntosRuta)
    }

    override fun mostrarProgress(mensaje: String) {
        progress_layout_map_mi_ruta?.visibility = View.VISIBLE
        text_mapa_progress_un_elemento?.text = mensaje
    }

    override fun ocultarProgress() {
        progress_layout_map_mi_ruta?.visibility = View.INVISIBLE
    }

    override fun mostrarMensaje(mensaje: String) {
        context?.showSnackBar(mensaje, coordinator_mapa_un_elemento)
    }

    override fun mostrarDialogSinConexion() {
        val fragment = SinConexionFragment.newInstance()
        fragmentManager?.let { fragment.show(it, fragment.tag) }
    }

    override fun mostrarProgressPerfil() {
        senderEstadoProgress.mostrarProgress()
    }

    override fun ocultarProgressPerfil() {
        senderEstadoProgress.ocultarProgress()
    }

    override fun irAPerfil(personIdentifier: PersonIdentifier) {
        if (!isResumed) return
        PerfilBuilder
            .conParametros(personIdentifier)
            .redirigirAAcompaniamiento()
            .recuperarFragment()
            .showOnce(childFragmentManager)
    }

    override fun mostrarErrorConexionPerfil(rol: Rol) {
        val fragment = ErrorDescargaInformacionDialogFragment.newInstance(
            ErrorDescargaInformacionDialogFragment.TipoMensaje.MENSAJE_POR_ROL, rol
        )
        fragment.show(childFragmentManager, "")
    }
}
