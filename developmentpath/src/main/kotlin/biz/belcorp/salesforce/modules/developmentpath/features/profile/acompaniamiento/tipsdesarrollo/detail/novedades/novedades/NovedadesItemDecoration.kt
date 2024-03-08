package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class NovedadesItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        if (position == (parent.adapter?.itemCount ?: 0) - 1) {
            outRect.set(0, 0, 0, 0)
            return
        }
        outRect.set(0, 0, space, 0)
    }
}
