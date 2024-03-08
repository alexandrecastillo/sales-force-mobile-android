package biz.belcorp.salesforce.modules.developmentpath.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*

/**
 * Created by Soporte on 23/02/2018.
 * Functions extensions for Google Map
 */

/** Módulo para enfocar los puntos LatLng
 * @param activity Actividad padre
 * @param widthScreen Ancho del contenedor del mapa
 * @param heightScreen Alto del contenedor del mapa
 * @param duration Tiempo de zoom y enfoque en milisegundos
 * @param padding Espacio desde los puntos hasta los bordes del contenedor
 */
fun GoogleMap.focus(builder: LatLngBounds, activity: Activity,
                    widthScreen: Int, heightScreen: Int,
                    duration: Int = 1000, padding: Int = 160) {
    var width = widthScreen
    var height = heightScreen
    if (width == 0 || height == 0) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = activity.resources.displayMetrics.widthPixels
        height = activity.resources.displayMetrics.heightPixels
    }
    this.animateCamera(CameraUpdateFactory.newLatLngBounds(builder, width, height, padding),
            duration, null)
}

fun GoogleMap.focus(location: LatLng, zoom: Float = 16f, duration: Int = 1000) {
    this.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom), duration, null)
}

fun GoogleMap.focus() {}

fun GoogleMap.drawRoute(puntos: List<RespuestaRuta.LatLon>, context: Context): Polyline {

    val PATTERN_DASH_LENGTH_PX = 40f
    val PATTERN_GAP_LENGTH_PX = 15f
    val DASH = Dash(PATTERN_DASH_LENGTH_PX)
    val GAP = Gap(PATTERN_GAP_LENGTH_PX)
    val PATTERN_POLYGON_ALPHA = arrayListOf(DASH, GAP)

    val options = PolylineOptions()
            .geodesic(true)
            .width(10f)
            .color(ContextCompat.getColor(context, R.color.rdd_linea_mapa))
            .pattern(PATTERN_POLYGON_ALPHA)

    puntos.forEach {
        val punto = LatLng(it.latitud, it.longitud)
        options.add(punto)
    }
    return this.addPolyline(options)
}

fun GoogleMap.mostrarAutoUbicacion(context: Context) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
        this.isMyLocationEnabled = true
    }
}

fun MapView.setButtonsBottomEnd() {
    findViewById<View>(Integer.parseInt("1"))?.also { view1 ->
        (view1.parent as? View)?.findViewById<View>(Integer.parseInt("2"))?.also { view2 ->
            val rlp = view2.layoutParams as RelativeLayout.LayoutParams
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            rlp.setMargins(0, 0, 180, 30)
        }
    }
}
