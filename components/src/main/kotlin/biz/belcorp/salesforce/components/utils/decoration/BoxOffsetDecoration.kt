package biz.belcorp.salesforce.components.utils.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

class BoxOffsetDecoration(
    private val verticalOffset: Int,
    private val horizontalOffset: Int,
    private val enableTop: Boolean = false,
    private val enableBottom: Boolean = false
) : RecyclerView.ItemDecoration() {

    constructor(
        context: Context,
        verticalOffset: Int,
        horizontalOffset: Int,
        enableTop: Boolean = false,
        enableBottom: Boolean = false
    ) : this(
        context.resources.getDimensionPixelSize(verticalOffset),
        context.resources.getDimensionPixelSize(horizontalOffset),
        enableTop,
        enableBottom
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        val totalItem = parent.adapter?.itemCount ?: 0

        val margins = if (totalItem == NUMBER_ONE) Rect(
            horizontalOffset,
            if (enableTop) verticalOffset else NUMBER_ZERO,
            horizontalOffset,
            if (enableBottom) verticalOffset else NUMBER_ZERO
        )
        else createMargins(position, parent.adapter?.itemCount ?: 0)

        outRect.set(margins)
    }

    private fun createMargins(adapterPosition: Int, itemCount: Int): Rect {
        return when {
            isFirstPosition(adapterPosition) -> createRect(
                horizontalOffset,
                if (enableTop) verticalOffset else NUMBER_ZERO,
                horizontalOffset,
                if (itemCount == NUMBER_ONE) verticalOffset else verticalOffset / NUMBER_TWO
            )
            isLastPosition(adapterPosition, itemCount) -> createRect(
                horizontalOffset,
                verticalOffset / NUMBER_TWO,
                horizontalOffset,
                if (enableBottom) verticalOffset else NUMBER_ZERO
            )
            else -> createRect(
                horizontalOffset,
                verticalOffset / NUMBER_TWO,
                horizontalOffset,
                verticalOffset / NUMBER_TWO
            )
        }
    }

    private fun isFirstPosition(adapterPosition: Int) = adapterPosition == NUMBER_ZERO

    private fun isLastPosition(adapterPosition: Int, itemCount: Int) =
        adapterPosition == itemCount - NUMBER_ONE

    private fun createRect(left: Int, top: Int, right: Int, bottom: Int): Rect {
        return Rect(left, top, right, bottom)
    }
}
