package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
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
import com.google.android.gms.maps.model.Polyline
import kotlinx.android.synthetic.main.fragment_mi_ruta_mapa.*

class MiRutaMapaFragment : MapaBaseFragment(), MapaView, MapaBaseFragment.EventosMapa {

    override fun getLayout() = R.layout.fragment_mi_ruta_mapa

    var acciones: ListenerAccionesEnMapa? = null

    private val presenter: MapaPresenter by injectFragment()

    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()

    private val firebasePresenter: FirebaseAnalyticsPresenter by injectFragment()

    private var marcadores = ListaMarcadores(ArrayList())
    private var polylineRuta: Polyline? = null
    private var planificadas: List<PersonaEnMapaModel> = emptyList()
    private var marcadoresPersonasCerca = ArrayList<Marker>()
    private var vieneDeCercanas = false
    private var primeraInstancia = false
    private var planId = -1L

    companion object {

        private const val ROL = "ROL"
        private const val PLAN_ID = "PLAN_ROL"
        private lateinit var rol: Rol

        fun newInstance(rol: Rol, planId: Long): MiRutaMapaFragment {
            val args = Bundle()
            val fragment = MiRutaMapaFragment()
            args.putSerializable(ROL, rol)
            args.putLong(PLAN_ID, planId)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planId = it.getLong(PLAN_ID)
            rol = it.getSerializable(ROL) as Rol
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = this
        presenter.rol = rol
        primeraInstancia = true

        obtenerDimensiones(view)
        inicializarVista(savedInstanceState, view)
        escucharAcciones()

    }

    private fun inicializarVista(savedInstanceState: Bundle?, view: View) {
        txt_rdd_mapa_texto_cercanas.text = resources.getString(
            R.string.rdd_mapa_text_cercanas,
            presenter.devolverTextoPorRol()
        )
        mapView = view.findViewById(R.id.map_view_mi_ruta)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun alCargarMapa() {
        acciones?.alCargarMapa()
    }

    override fun alRechazarActivarGPS() {
        enfocarMapa(marcadores)
        ocultarProgress()
        mostrarMensaje("Es necesario activar el GPS.")
    }

    override fun alClickearMarcador(marcador: Marker) {
        val persona = marcador.tag
        if (persona is PersonaEnMapaModel) {
            val personIdentifier = PersonIdentifier(persona.personaId, persona.personCode, persona.rol)
            irAPerfil(personIdentifier)
            firebasePresenter.enviarEventoMarcador(
                TagAnalytics.EVENTO_MARCAR_MAPA,
                persona.personaId, persona.rol
            )
        }
    }

    override fun alRecuperarUbicacion() = guardarUltimaUbicacion()

    override fun alEsperarUbicacion() = mostrarProgress("Obteniendo ubicación...")

    override fun cargar(personas: PersonaEnMapaViewModel) {
        if (context == null) return

        limpiarMarcadores()
        planificadas = personas.planificadas
        cargarMarcadores()
        if (enPrimerPlano && !primeraInstancia) {
            recuperarUbicacion()
        }
        primeraInstancia = false
    }

    override fun refrescarMapa() {
        cancelarDelayEnJob()
        recuperarUbicacionGoogleApi()
    }

    override fun showRoute(puntos: List<RespuestaRuta.LatLon>) {
        limpiarRuta()
        polylineRuta = map?.drawRoute(puntos, context ?: return)
    }

    override fun mostrarPersonasCerca(personasCerca: List<PersonaEnMapaModel>) =
        agregarMarcadores(filtrarPersonasYaMarcadas(personasCerca))

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    private fun filtrarPersonasYaMarcadas(personasCerca: List<PersonaEnMapaModel>):
        List<PersonaEnMapaModel> {

        personasCerca.forEach { it.marcarComoCercana() }
        val listaIds = planificadas.map { it.personaId }
        return personasCerca.filter { !listaIds.contains(it.personaId) }
    }

    private fun cargarMarcadores() {
        agregarMarcadores(planificadas)
        if (switch_mapa_cercanas.isChecked) {
            buscarPersonasCerca()
        }
    }

    private fun agregarMarcadores(personas: List<PersonaEnMapaModel>) {
        if (!isAttached()) return
        val suministrador = inflarLayoutsMarkers()
        personas.forEach { agregarPersonaAMarcadores(it, suministrador) }
        enfocarMapa(marcadores)
    }

    private fun agregarPersonaAMarcadores(
        persona: PersonaEnMapaModel,
        suministrador: SuministradorImagenMarcador
    ) {
        with(persona) {
            if (persona.ubicacion.poseeCoordenadas) {
                val latLng = LatLng(ubicacion.latitud!!, ubicacion.longitud!!)
                val marker = map?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(suministrador.recuperarImagen(this))
                )
                marker?.tag = this
                if (estaCerca) {
                    marcadoresPersonasCerca.add(marker ?: return)
                }
                marcadores.insertar(
                    Marcador(
                        latLng = latLng,
                        esUbicacion = false,
                        esParteDeRuta = !tieneAlgunaVisitaRegistrada && !estaCerca,
                        estaCerca = estaCerca,
                        id = personaId
                    )
                )
            }
        }
    }

    private fun guardarUltimaUbicacion() {
        if (ubicacion != null) {
            val latLng = LatLng(ubicacion!!.latitude, ubicacion!!.longitude)
            marcadores.insertar(Marcador(latLng = latLng, esUbicacion = true, id = 0))
            decidirSolicitarRuta()
        } else {
            mostrarMensaje("No se pudo get la ubicación.")
            ocultarProgress()
        }
        enfocarMapa(marcadores)
    }

    private fun decidirSolicitarRuta() {
        if (!vieneDeCercanas) {
            solicitarRuta()
        }
    }

    private fun solicitarRuta() = presenter.solicitarRuta(marcadores)

    private fun limpiarRuta() = polylineRuta?.remove()

    private fun limpiarMarcadores() {
        marcadores.eliminarEscalasYCercanas()
        marcadoresPersonasCerca.clear()
        map?.clear()
    }

    private fun buscarPersonasCerca() = acciones?.alBuscarPersonasCerca(marcadores)

    private fun ocultarPersonasCerca() {
        marcadoresPersonasCerca.forEach { it.remove() }
        marcadores.eliminarCercanas()
        enfocarMapa(marcadores)
    }

    override fun mostrarProgress(mensaje: String) {
        progress_layout_map_mi_ruta?.visible()
        text_mapa_progress?.text = mensaje
    }

    override fun ocultarProgress() {
        progress_layout_map_mi_ruta?.invisible()
    }

    private fun escucharAcciones() {
        button_rdd_mapa_actualizar_ruta?.setOnClickListener { recuperarUbicacionGoogleApi() }

        switch_mapa_cercanas?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vieneDeCercanas = true
                recuperarUbicacion()
                vieneDeCercanas = false
                buscarPersonasCerca()
                firebasePresenter.enviarEvento(TagAnalytics.EVENTO_SWITCH_ACTIVAR_MAPA)

            } else {
                ocultarPersonasCerca()
            }
        }
    }

    override fun mostrarMensaje(mensaje: String) {
        context?.apply { showSnackBar(mensaje, coordinator_mapa) }
    }

    override fun mostrarDialogSinConexion() = Unit

    override fun mostrarProgressPerfil() {
        senderEstadoProgress.mostrarProgress()
    }

    override fun ocultarProgressPerfil() {
        senderEstadoProgress.ocultarProgress()
    }

    override fun irAPerfil(personIdentifier: PersonIdentifier) {
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
