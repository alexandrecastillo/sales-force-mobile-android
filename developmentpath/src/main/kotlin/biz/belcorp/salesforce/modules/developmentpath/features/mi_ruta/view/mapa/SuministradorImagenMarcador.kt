package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.components.utils.textToRoundDrawable
import biz.belcorp.salesforce.core.utils.toBitmap
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class SuministradorImagenMarcador(private val layoutHoy: View,
                                  private val imageHoy: ImageView,
                                  private val layoutNoHoy: View,
                                  private val imageNoHoy: ImageView,
                                  private val imageOrden: ImageView,
                                  val context: Context) {

    fun recuperarImagen(persona: PersonaEnMapaModel): BitmapDescriptor {

        val bitmap =
                if (!persona.estaCerca) cargarImagenPrimaria(persona)
                else cargarImagenSecuandaria(persona)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun cargarImagenPrimaria(persona: PersonaEnMapaModel): Bitmap {
        val avatarDrawable = textToRoundDrawable(
                text = persona.iniciales,
                color = ContextCompat.getColor(context, R.color.rdd_accent))
        if (!persona.tieneAlgunaVisitaRegistrada) {
            imageOrden.visibility = View.INVISIBLE
        } else {
            imageOrden.visibility = View.VISIBLE
        }
        imageHoy.setImageDrawable(avatarDrawable)
        return layoutHoy.toBitmap(context)
    }

    private fun cargarImagenSecuandaria(persona: PersonaEnMapaModel): Bitmap {
        imageOrden.visibility = View.INVISIBLE
        val avatarDrawable = textToRoundDrawable(
                text = persona.iniciales,
                color = ContextCompat.getColor(context, R.color.rdd_mapa_marker_bg_gray))
        imageNoHoy.setImageDrawable(avatarDrawable)
        return layoutNoHoy.toBitmap(context)
    }
}
