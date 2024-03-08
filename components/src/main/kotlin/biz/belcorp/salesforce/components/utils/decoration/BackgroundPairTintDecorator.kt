package biz.belcorp.salesforce.components.utils.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.utils.backgroundColor

class BackgroundPairTintDecorator(
    @ColorRes private val pairColor: Int,
    @ColorRes private val impairColor: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        view.apply {
            backgroundColor = if (position % NUMBER_TWO == NUMBER_ZERO) getColor(pairColor)
            else getColor(impairColor)
        }
    }
}
