package biz.belcorp.salesforce.components.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ONE_NEGATIVE
import kotlin.math.roundToInt
import biz.belcorp.mobile.components.design.R as ComponentsR

/**
 * Fix: GridLayout orientation HORIZONTAL
 * Fix: pinta lineas cuando no tiene elementos debajo
 *
 */
class LineDividerDecoration(
    private val context: Context,
    @DrawableRes private val drawable: Int = ONE_NEGATIVE,
    @DimenRes private val inset: Int,
    private val useAll: Boolean = false
) : RecyclerView.ItemDecoration() {

    private var mColor: Int = ONE_NEGATIVE
    private val outBounds = Rect()
    private val currentInset = context.resources.getDimensionPixelOffset(inset)
    private val elementsOffset =
        context.resources.getDimensionPixelOffset(ComponentsR.dimen.content_inset_tiniest)
    private var currentSpanCount: Int? = null

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (parent.layoutManager) {
            is GridLayoutManager -> {
                val gridManager = (parent.layoutManager as GridLayoutManager)
                currentSpanCount = gridManager.spanCount
                buildGridOffsets(
                    Params(
                        parent.getChildAdapterPosition(view),
                        parent.adapter?.itemCount ?: NUMBER_ZERO,
                        gridManager.spanCount,
                        gridManager.orientation,
                        outRect
                    )
                )
            }
            is LinearLayoutManager -> {
                val linearManager = (parent.layoutManager as LinearLayoutManager)
                buildLinearOffsets(
                    Params(
                        parent.getChildAdapterPosition(view),
                        parent.adapter?.itemCount ?: NUMBER_ZERO,
                        ONE_NEGATIVE,
                        linearManager.orientation,
                        outRect
                    )
                )
            }
        }

    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (drawable == ONE_NEGATIVE || parent.layoutManager == null) {
            super.onDraw(canvas, parent, state)
            return
        }
        val drawable = getDrawable() ?: return
        when (parent.layoutManager) {
            is GridLayoutManager -> {
                val gridManager = (parent.layoutManager as GridLayoutManager)
                drawGridDivider(canvas, parent, drawable, gridManager.orientation)
            }
            is LinearLayoutManager -> {
                val linearManager = (parent.layoutManager as LinearLayoutManager)
                drawLinearDivider(canvas, parent, drawable, linearManager.orientation)
            }
        }
    }

    fun getDrawable(): Drawable? {
        val drawable = context.getDrawable(drawable)
        if (mColor != ONE_NEGATIVE) drawable?.colorFilter =
            PorterDuffColorFilter(mColor, PorterDuff.Mode.DST_ATOP)
        return drawable
    }

    private fun drawGridDivider(
        canvas: Canvas,
        parent: RecyclerView,
        drawable: Drawable,
        orientation: Int
    ) {
        when {
            useAll -> {
                drawGridVertical(canvas, parent, drawable)
                drawGridHorizontal(canvas, parent, drawable)
            }
            orientation == RecyclerView.HORIZONTAL -> {
                drawGridHorizontal(canvas, parent, drawable)
            }
            else -> drawGridVertical(canvas, parent, drawable)
        }
    }

    private fun drawLinearDivider(
        canvas: Canvas,
        parent: RecyclerView,
        drawable: Drawable,
        orientation: Int
    ) {
        when (orientation) {
            RecyclerView.HORIZONTAL -> drawLinearHorizontal(canvas, parent, drawable)
            else -> drawLinearVertical(canvas, parent, drawable)
        }
    }

    private fun drawLinearVertical(canvas: Canvas, parent: RecyclerView, drawable: Drawable) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, outBounds)
            val left = outBounds.left + child.translationX.roundToInt() + currentInset
            val bottom = outBounds.bottom
            val top = bottom - drawable.intrinsicHeight
            val right = outBounds.right - currentInset
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawLinearHorizontal(canvas: Canvas, parent: RecyclerView, drawable: Drawable) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, outBounds)
            val top = outBounds.top + currentInset
            val bottom = outBounds.bottom + child.translationY.roundToInt() - currentInset
            val right = outBounds.right + child.translationY.roundToInt()
            val left = right - drawable.intrinsicWidth
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawGridVertical(canvas: Canvas, parent: RecyclerView, drawable: Drawable) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            if (isRightElement(i, currentSpanCount ?: return) || child == null) continue
            parent.getDecoratedBoundsWithMargins(child, outBounds)
            val bottom = (outBounds.bottom + child.translationY.roundToInt()) - currentInset
            val right = outBounds.right + child.translationX.roundToInt()
            val left = right - drawable.intrinsicWidth
            val top = outBounds.top + currentInset
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawGridHorizontal(canvas: Canvas, parent: RecyclerView, drawable: Drawable) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val hasOneRow = hasOneRow(childCount, currentSpanCount ?: return)
            val isLastRow = isLastRow(i, childCount, currentSpanCount ?: return)
            val isRight = isRightElement(i, currentSpanCount ?: return)
            if (hasOneRow || isLastRow || child == null) continue
            parent.getDecoratedBoundsWithMargins(child, outBounds)
            val offset = if (isRight) NUMBER_ZERO else elementsOffset
            val right =
                outBounds.right + child.translationX.roundToInt() - offset - currentInset
            val left = outBounds.left + currentInset
            val top = child.height
            val bottom =
                top + child.translationY.roundToInt() + drawable.intrinsicWidth
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun buildGridOffsets(params: Params) = with(params) {
        when {
            hasOneRow(totalElements, spanCount) -> createForOneRow(this)
            else -> createOtherOffset(this)
        }
    }

    private fun buildLinearOffsets(params: Params) = with(params) {
        if (isLastElement(position, totalElements)) return@with
        when (totalElements) {
            NUMBER_ZERO, NUMBER_ONE -> return@with
            else -> createLinearOffsets(this)
        }
    }

    private fun createLinearOffsets(params: Params) = with(params) {
        when (orientation) {
            RecyclerView.HORIZONTAL -> outRect.set(
                NUMBER_ZERO,
                NUMBER_ZERO,
                elementsOffset,
                NUMBER_ZERO
            )
            else -> outRect.set(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, elementsOffset)
        }
        outRect
    }

    private fun createForOneRow(params: Params): Rect = with(params) {
        val isRight = isRightElement(position, spanCount)
        val rightOffset = if (isRight) NUMBER_ZERO else elementsOffset
        when (orientation) {
            RecyclerView.HORIZONTAL -> {
                outRect.set(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO)
            }
            else -> outRect.set(NUMBER_ZERO, NUMBER_ZERO, rightOffset, NUMBER_ZERO)
        }
        outRect
    }

    private fun createOtherOffset(params: Params) = with(params) {
        val isRight = isRightElement(position, spanCount)
        val isLastRow = isLastRow(position, totalElements, spanCount)

        val rightOffset = if (isRight) NUMBER_ZERO else elementsOffset
        val bottomOffset = if (isLastRow) NUMBER_ZERO else elementsOffset
        when {
            useAll -> outRect.set(NUMBER_ZERO, NUMBER_ZERO, rightOffset, bottomOffset)
            orientation == RecyclerView.HORIZONTAL -> {
                outRect.set(NUMBER_ZERO, NUMBER_ZERO, NUMBER_ZERO, bottomOffset)
            }
            else -> outRect.set(NUMBER_ZERO, NUMBER_ZERO, rightOffset, NUMBER_ZERO)
        }
        outRect
    }

    private fun isFirstRow(position: Int, spanCount: Int) = position <= spanCount

    private fun isLastRow(position: Int, totalElements: Int, spanCount: Int): Boolean {
        val isMultiple = (totalElements % spanCount) == NUMBER_ZERO
        val leftRange = if (isMultiple) (totalElements - spanCount)
        else (totalElements / spanCount) * spanCount
        return position in leftRange..totalElements
    }

    private fun hasOneRow(totalElements: Int, spanCount: Int) = isFirstRow(totalElements, spanCount)

    private fun isRightElement(position: Int, spanCount: Int): Boolean {
        return position >= spanCount - NUMBER_ONE || (position + NUMBER_ONE) % spanCount == NUMBER_ZERO
    }

    private fun isLastElement(position: Int, totalElements: Int) =
        position == totalElements - NUMBER_ONE

    private class Params(
        val position: Int,
        val totalElements: Int,
        val spanCount: Int,
        val orientation: Int,
        val outRect: Rect
    )

}
