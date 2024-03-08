package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.gps.RequestGPS
import biz.belcorp.salesforce.core.gps.RequestListenerGPS
import biz.belcorp.salesforce.core.gps.SubscriberListenerGPS
import biz.belcorp.salesforce.core.utils.DeviceUtil
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores
import biz.belcorp.salesforce.modules.developmentpath.utils.focus
import biz.belcorp.salesforce.modules.developmentpath.utils.mostrarAutoUbicacion
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.*

abstract class MapaBaseFragment : BaseFragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    RequestListenerGPS {

    private val requestGPS by inject<RequestGPS>()

    open var mapView: MapView? = null
    open lateinit var listener: EventosMapa
    var enPrimerPlano = true
    private lateinit var listenerGPS: SubscriberListenerGPS
    var map: GoogleMap? = null
    var googleClient: GoogleApiClient? = null
    var ubicacion: Location? = null
    private var job: Job? = null
    private var widthScreen: Int = 0
    private var heightScreen: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listenerGPS = activity as SubscriberListenerGPS
    }

    override fun onStart() {
        mapView?.onStart()
        super.onStart()
    }

    override fun onResume() {
        mapView?.onResume()
        listenerGPS.subscribeListener(this)
        super.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView?.onStop()
        listenerGPS.unsubscribeListener()
        super.onStop()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        enPrimerPlano = !hidden
        super.onHiddenChanged(hidden)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verificarPermiso()
        } else {
            verificarConfiguracionUbicacion()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.mostrarAutoUbicacion(context!!)
        map?.setOnMarkerClickListener(this)
        listener.alCargarMapa()
    }

    override fun onMarkerClick(marcador: Marker?): Boolean {
        marcador?.let { listener.alClickearMarcador(marcador) }
        return true
    }

    private fun verificarPermiso() {
        if (context == null) return

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PermissionsUtil.LOCATION_PERMISSION
            )
        } else {
            verificarConfiguracionUbicacion()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionsUtil.LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map?.mostrarAutoUbicacion(context!!)
                    verificarConfiguracionUbicacion()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun notifyRequestGPS(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        map?.mostrarAutoUbicacion(requireContext())
                        recuperarUbicacionGoogleApi()
                    }
                    Activity.RESULT_CANCELED -> {
                        listener.alRechazarActivarGPS()
                    }
                }
            }
        }
    }

    private fun verificarConfiguracionUbicacion() {
        if (DeviceUtil.hasGPS(requireContext())) {
            val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                requestGPS.requestGPS(requireActivity())
            } else {
                map?.mostrarAutoUbicacion(requireContext())
                recuperarUbicacionGoogleApi()
            }
        }
    }

    fun recuperarUbicacionGoogleApi() {
        if (!isAttached()) return
        googleClient = GoogleApiClient.Builder(context ?: return)
            .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(bundle: Bundle?) {
                    recuperarUbicacion()
                }

                override fun onConnectionSuspended(p0: Int) {
                    googleClient?.connect()
                }
            })
            .addOnConnectionFailedListener { }
            .addApi(LocationServices.API).build()
        googleClient?.connect()
    }

    fun recuperarUbicacion() {
        recuperarUltimaUbicacion()
        if (ubicacion == null) {
            recuperarUltimaUbicacionConDelay()
        } else {
            listener.alRecuperarUbicacion()
        }
    }

    private fun recuperarUltimaUbicacionConDelay(espera: Long = 3000) {
        job = GlobalScope.launch(context = Dispatchers.Main) {
            listener.alEsperarUbicacion()
            delay(espera)
            recuperarUltimaUbicacion()
            listener.alRecuperarUbicacion()
        }
    }

    private fun recuperarUltimaUbicacion() {
        if (context == null) return
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setearUbicacion()
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun setearUbicacion() {
        if (googleClient != null) {
            ubicacion = LocationServices.FusedLocationApi.getLastLocation(googleClient)
        }
    }

    fun cancelarDelayEnJob() = job?.cancel()

    fun obtenerDimensiones(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            widthScreen = view.width
            heightScreen = view.height
        }
        view.viewTreeObserver.removeOnDrawListener { }
    }

    fun inflarLayoutsMarkers(): SuministradorImagenMarcador {

        val nullParent: ViewGroup? = null
        val layoutHoy = LayoutInflater.from(context)
            .inflate(R.layout.marker_custom, nullParent, false)
        val layoutNoHoy = LayoutInflater.from(context)
            .inflate(R.layout.marker_custom_secundary, nullParent, false)
        val imageHoy = layoutHoy.findViewById<ImageView>(R.id.image_custom_marker_avatar)
        val imageNoHoy =
            layoutNoHoy.findViewById<ImageView>(R.id.image_custom_marker_avatar_secundary)
        val imageOrden = layoutHoy.findViewById<ImageView>(R.id.image_custom_marker_check_visitada)
        return SuministradorImagenMarcador(
            layoutHoy, imageHoy, layoutNoHoy,
            imageNoHoy, imageOrden, context!!
        )
    }

    fun enfocarMapa(marcadores: ListaMarcadores) {
        if (activity == null) return

        val bounds = marcadores.convertirABoundsBuilder()
        when (marcadores.size()) {
            0 -> map?.focus()
            1 -> map?.focus(bounds.build().center)
            else -> map?.focus(bounds.build(), activity!!, widthScreen, heightScreen)
        }
    }

    interface EventosMapa {

        fun alCargarMapa()

        fun alRechazarActivarGPS()

        fun alClickearMarcador(marcador: Marker)

        fun alRecuperarUbicacion()

        fun alEsperarUbicacion()
    }
}
