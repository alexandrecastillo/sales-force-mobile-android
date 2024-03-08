package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.dip

class ConsolidadoAcuerdosDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val size = parent.adapter?.itemCount ?: return
        with(outRect) {
            if (size > 0 && position != size - 1) {
                right = view.context.dip(2)
            }
        }
    }
}
