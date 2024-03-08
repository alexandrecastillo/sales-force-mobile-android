package biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FilterConstancyMarginDecoration(private val margin: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {

        with(outRect) {
            margin.let {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = it
                }
                right = it
            }
        }
    }
}

