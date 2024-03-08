package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSeccionDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val cantidadElementos = parent.adapter?.itemCount ?: return
            val posicion = parent.getChildAdapterPosition(view)
            if (posicion == 0) {
                left = margin
            }
            right = if (posicion == cantidadElementos - 1) {
                margin
            } else {
                margin / 2
            }
        }
    }
}
