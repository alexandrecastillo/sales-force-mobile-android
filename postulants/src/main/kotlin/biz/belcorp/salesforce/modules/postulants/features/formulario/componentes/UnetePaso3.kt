package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.view.View
import androidx.core.app.ActivityCompat
import biz.belcorp.salesforce.core.utils.delay
import biz.belcorp.salesforce.core.utils.longToast
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.UnetePaso3View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.setButtonsBottomEnd
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.unete_paso_3.view.*


@SuppressLint("ViewConstructor")
class UnetePaso3(mContext: Context, view: UnetePaso3View) :
    BaseUnetePaso<UnetePaso3View>(mContext, view),
    IUnetePaso3 {

    private var map: GoogleMap? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var location: Location? = null
    private var listener: ListenerGpsNoActivo? = null

    override fun getLayout() = R.layout.unete_paso_3


    private fun initMap() {
        mapView?.onCreate(null)
        mapView?.getMapAsync { googleMap ->
            map = googleMap
            view.checkLocationPermission {
                setLocationMap()
                MapsInitializer.initialize(context)
            }
        }
        mapView?.onResume()
        mapView?.setButtonsBottomEnd()
    }

    private fun setLocationMap() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        map?.isMyLocationEnabled = true
        mFusedLocationClient?.lastLocation
            ?.addOnSuccessListener(context as Activity) { locationUpdated ->
                if (locationUpdated != null) {
                    location = locationUpdated
                    val latitude =
                        if (view.getModel().tieneLocation) view.getModel().latitud?.toDouble().zeroIfNull() else locationUpdated.latitude
                    val longitude =
                        if (view.getModel().tieneLocation) view.getModel().longitud?.toDouble().zeroIfNull() else locationUpdated.longitude
                    moverMapCamaraA(latitude, longitude)
                    view.validateMapRadio()
                } else {
                    listener?.requestGPS()
                }
            }
    }

    override fun drawCircleRestriction(radio: Int) {
        if (radio > 0) {
            val centerLatitude =
                if (view.getModel().tieneLocation) view.getModel().latitud!!.toDouble() else location?.latitude
            val centerLongitude =
                if (view.getModel().tieneLocation) view.getModel().longitud!!.toDouble() else location?.longitude
            initMapMove(centerLatitude!!, centerLongitude!!, radio)
            map?.setOnCameraMoveListener {
                validatePanning(radio)
            }
        }
    }

    private fun validatePanning(radius: Int) {
        val target = map?.cameraPosition!!.target
        val centerLatitude =
            if (view.getModel().tieneLocation) view.getModel().latitud!!.toDouble() else location?.latitude
        val centerLongitude =
            if (view.getModel().tieneLocation) view.getModel().longitud!!.toDouble() else location?.longitude

        val currentRadius = obtenerRadioActual(LatLng(centerLatitude!!, centerLongitude!!), target)

        if (currentRadius > radius) {
            view.enableContinueButton(false)
        } else {
            view.enableContinueButton(true)
        }

    }

    fun obtenerRadioActual(posicionCentral: LatLng, posicionFinal: LatLng): Double {
        return view.getCurrentRadius(posicionCentral, posicionFinal)
    }

    private fun initMapMove(latitud: Double, longitud: Double, radio: Int) {
        val center = LatLng(latitud, longitud)
        drawCircle(center, radio)
        limitNavigation(center, radio)
    }

    private fun drawCircle(point: LatLng, radius: Int) {

        val circleOptions = CircleOptions()
        circleOptions.center(point)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.RED)
        circleOptions.strokeWidth(4f)
        map?.addCircle(circleOptions)

    }

    private fun limitNavigation(point: LatLng, origRadio: Int) {

        val newLatitude = origRadio / (Constant.RADIO_PLANETA * 1000) * (180 / Math.PI)
        val newLongitude =
            origRadio / (Constant.RADIO_PLANETA * 1000) * (180 / Math.PI) / Math.cos(newLatitude * Math.PI / 180)

        val limitBounds = LatLngBounds(
            LatLng(point.latitude - newLatitude, point.longitude - newLongitude),
            LatLng(point.latitude + newLatitude, point.longitude + newLongitude)
        )

        map?.setLatLngBoundsForCameraTarget(limitBounds)
    }

    private fun moverMapCamaraA(latitude: Double, longitude: Double) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 16f)
        map?.moveCamera(cameraUpdate)
    }

    override fun initControls() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Activity)
        initMap()
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel()

        model.direccion = crearDireccion(model.direccion)
        model.paso = 3

        val ltlg = getMarkerLocation()

        model.latitud = ltlg?.latitude.toString()
        model.longitud = ltlg?.longitude.toString()
        model.sincronizado = false

        return model
    }

    override fun loadModel() {
        val model = view.getModel()

        txtDirecccion?.setText(model.direccion?.split("|")?.lastOrNull())
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtDirecccion!!
                )
            )
        }

        return lst
    }

    private fun crearDireccion(direccion: String?): String {
        return direccion
            ?.split("|")
            ?.takeIf { it.size > 1 }
            ?.toMutableList()
            ?.also {
                it.removeAt(it.lastIndex)
                it.add(txtDirecccion?.getText()!!)
            }
            ?.joinToString("|") ?: txtDirecccion?.getText()!!
    }

    private fun getMarkerLocation(): LatLng? {
        return map?.cameraPosition?.target
    }

    override fun initRestriction() {
        txtDirecccion?.setR(Filters.filterMaxLength(140))
    }

    override fun validate(): Boolean {
        if (location == null && mapView != null) {
            context?.longToast(R.string.mensaje_zonificacion)
        }
        return txtDirecccion?.validate()!! && location != null
    }

    override fun initRequired() {
        txtDirecccion?.setRequired(true)
    }

    override fun initValidation() {
        txtDirecccion?.addV(V(context, EMPTY, R.string.unete_valid_obligatorio))
    }

    override fun onGPSRequestSuccess() {
        delay(3000) {
            setLocationMap()
        }
    }

    override fun onGPSRequestActivated() {
        setLocationMap()
    }

    override fun registerListenerGSP(listener: ListenerGpsNoActivo?) {
        this.listener = listener
        listener?.requestGPS()
    }

    override fun unRegisterListenerGSP() {
        listener = null
    }

    interface ListenerGpsNoActivo {
        fun requestGPS()
    }

}
