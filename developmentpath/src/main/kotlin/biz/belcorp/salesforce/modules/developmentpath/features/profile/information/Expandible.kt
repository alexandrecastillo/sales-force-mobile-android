package biz.belcorp.salesforce.modules.developmentpath.features.profile.information

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.modules.developmentpath.R

class Expandible(
    private val layout: ViewGroup,
    private val fadedView: View,
    private val toggleIcon: ImageView,
    private var expandido: Boolean = false
) {

    var isExpandido = expandido

    fun invertirEstado() {
        expandido = !expandido
        isExpandido = expandido
        redibujar()
    }

    fun redibujar() {
        fadedView.visibility = obtenerVisibilidadSegunEstado()
        toggleIcon.setImageDrawable(obtenerIconoSegunEstado())
        layout.layoutParams?.height = obtenerAlturaSegunEstado()
        layout.requestLayout()
    }

    private fun obtenerAlturaSegunEstado(): Int? {
        return if (expandido) {
            ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layout.context?.resources?.getDimension(R.dimen.expandable_height)?.toInt()
        }
    }

    private fun obtenerVisibilidadSegunEstado(): Int {
        return if (expandido) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun obtenerIconoSegunEstado(): Drawable? {
        return if (expandido) {
            ContextCompat.getDrawable(toggleIcon.context, R.drawable.ic_arrow_up)
        } else {
            ContextCompat.getDrawable(toggleIcon.context, BaseR.drawable.ic_arrow_down)
        }
    }
}
