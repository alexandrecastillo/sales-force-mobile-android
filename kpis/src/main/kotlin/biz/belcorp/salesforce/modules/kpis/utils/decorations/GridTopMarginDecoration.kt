package biz.belcorp.salesforce.modules.kpis.utils.decorations

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

class GridTopMarginDecoration(
    context: Context,
    private val spanCount: Int = NUMBER_TWO,
    private val enableTopElement: Boolean = true,
    @DimenRes val verticalDimension: Int,
    @DimenRes val horizontalDimension: Int
) : RecyclerView.ItemDecoration() {

    private val vDimen = context.resources.getDimensionPixelSize(verticalDimension)
    private val hDimen = context.resources.getDimensionPixelSize(horizontalDimension)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val children = parent.adapter?.itemCount ?: NUMBER_ONE
        val position = getChildPosition(parent, view)
        val midVerticalDimension = calculateMidDimension(vDimen)
        val mindHorizontalDimension = calculateMidDimension(hDimen)

        if (position == RecyclerView.NO_POSITION) return
        if (position == NUMBER_ZERO && enableTopElement) outRect.set(
            NUMBER_ZERO,
            NUMBER_ZERO,
            NUMBER_ZERO,
            midVerticalDimension
        )
        else {
            val isFirstRow = isFirstRow(position)
            val isLastRow = isLastRow(position, children)
            val dimensions = Pair(midVerticalDimension, mindHorizontalDimension)
            val margins = when {
                isFirstRow -> resolverMarginsFirstRow(position, dimensions)
                isLastRow -> resolveMarginsLastRow(position, dimensions)
                else -> resolveMargins(position, dimensions)
            }
            outRect.set(margins)
        }
    }

    private fun getChildPosition(recyclerView: RecyclerView, view: View): Int {
        return recyclerView.getChildAdapterPosition(view)
    }

    private fun calculateMidDimension(dimension: Int): Int {
        return if (dimension % NUMBER_TWO == NUMBER_ZERO) dimension / NUMBER_TWO
        else (dimension + NUMBER_ONE) / NUMBER_TWO
    }

    private fun resolverMarginsFirstRow(
        position: Int,
        dimensions: Pair<Int, Int>
    ): Rect {
        return when {
            isAtLeft(position) -> Rect(
                NUMBER_ZERO,
                NUMBER_ZERO,
                dimensions.second,
                dimensions.first
            )
            isAtCenter(position) -> Rect(
                dimensions.second,
                NUMBER_ZERO,
                dimensions.second,
                dimensions.first
            )
            isAtRight(position) -> Rect(
                dimensions.second,
                NUMBER_ZERO,
                NUMBER_ZERO,
                dimensions.first
            )
            else -> Rect(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO)
        }
    }

    private fun resolveMarginsLastRow(
        position: Int,
        dimensions: Pair<Int, Int>
    ): Rect {
        return when {
            isAtLeft(position) -> Rect(
                NUMBER_ZERO,
                dimensions.first,
                dimensions.second,
                NUMBER_ZERO
            )
            isAtCenter(position) -> Rect(
                dimensions.second,
                dimensions.first,
                dimensions.second,
                NUMBER_ZERO
            )
            isAtRight(position) -> Rect(
                dimensions.second,
                dimensions.first,
                NUMBER_ZERO,
                NUMBER_ZERO
            )
            else -> Rect(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO)
        }
    }

    private fun resolveMargins(position: Int, dimensions: Pair<Int, Int>): Rect {
        return when {
            isAtLeft(position) -> Rect(
                NUMBER_ZERO,
                dimensions.first,
                dimensions.second,
                dimensions.first
            )
            isAtCenter(position) -> Rect(
                dimensions.second,
                dimensions.first,
                dimensions.second,
                dimensions.first
            )
            isAtRight(position) -> Rect(
                dimensions.second,
                dimensions.first,
                NUMBER_ZERO,
                dimensions.first
            )
            else -> Rect(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO)
        }
    }

    private fun isFirstRow(position: Int) = position < spanCount

    private fun isLastRow(position: Int, totalElements: Int): Boolean {
        val modulus = totalElements % spanCount
        val useSpan = modulus == NUMBER_ZERO
        return if (useSpan) position in (totalElements - spanCount) until totalElements
        else position in (totalElements - modulus) until totalElements
    }

    private fun isAtLeft(position: Int): Boolean {
        return position % spanCount == if (enableTopElement) MINIMUM_AT_LEFT else NUMBER_ZERO
    }

    private fun isAtCenter(position: Int): Boolean {
        return (position % spanCount insideRange (MINIMUM_AT_CENTER until spanCount))
    }

    private fun isAtRight(position: Int): Boolean {
        return position % spanCount == if (enableTopElement) NUMBER_ZERO else NUMBER_ONE
    }

    private infix fun Int.insideRange(values: IntRange) = this in values

    companion object {
        private const val MINIMUM_AT_LEFT = NUMBER_ONE
        private const val MINIMUM_AT_CENTER = NUMBER_TWO
    }
}
