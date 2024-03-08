package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class EtiquetaInfoDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val posicion = parent.getChildAdapterPosition(view)
            val grupos = (parent.adapter as? EtiquetaInfoAdapter)?.grupos ?: return
            val modelo = grupos[posicion]
            aplicarMargenSuperior(modelo)
            aplicarMargenesLaterales(modelo)
        }
    }

    private fun Rect.aplicarMargenesLaterales(modelo: Grupo) {
        when {
            modelo.esUnicoElemento -> {
                left = margin
                right = margin
            }
            modelo.estaRodeado -> {
                left = margin / 2
                right = margin / 2
            }
            modelo.enInicioDeFila -> {
                left = margin
                right = margin / 2
            }
            modelo.enFinDeFila -> {
                left = margin / 2
                right = margin
            }
        }
    }

    private fun Rect.aplicarMargenSuperior(modelo: Grupo) {
        if (!modelo.perteneceAPrimeraFila) {
            top = margin
        }
    }
}
