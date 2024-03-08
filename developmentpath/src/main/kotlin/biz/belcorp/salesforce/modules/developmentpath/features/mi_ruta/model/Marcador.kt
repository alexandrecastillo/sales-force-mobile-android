package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import com.google.android.gms.maps.model.LatLng

data class Marcador(val latLng: LatLng,
                    val esUbicacion: Boolean,
                    val esParteDeRuta: Boolean = false,
                    val estaCerca: Boolean = false,
                    val id: Long = 0) {

    fun convertirAPair() = Pair(latLng.latitude, latLng.longitude)

    override fun toString() = latLng.latitude.toString() + "," + latLng.longitude.toString()
}
