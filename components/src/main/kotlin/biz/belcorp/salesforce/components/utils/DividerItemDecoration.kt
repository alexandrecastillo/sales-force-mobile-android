package biz.belcorp.salesforce.components.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class DividerItemDecoration(private val context: Context, orientation: Int) :
    RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    private var mOrientation: Int = 0

    private var verticalOffset = 0

    private var horizontalOffset = 0

    private val mBounds = Rect()

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(
                TAG,
                "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setDrawable()"
            )
        }
        a.recycle()
        setOrientation(orientation)
    }

    fun setDividerColor(color: Int) {
        mDivider?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL && orientation != ALL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        mOrientation = orientation
    }

    fun setDrawable(drawable: Drawable?) {
        mDivider = drawable
    }

    fun setVerticalOffset(@DimenRes offset: Int) {
        verticalOffset = context.resources.getDimensionPixelOffset(offset)
    }

    fun setHorizontalOffset(@DimenRes offset: Int) {
        horizontalOffset = context.resources.getDimensionPixelOffset(offset)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }

        when (mOrientation) {
            ALL -> {
                drawVertical(c, parent)
                drawHorizontal(c, parent)
            }
            VERTICAL -> drawVertical(c, parent)
            else -> drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            var leftItems = childCount % (parent.layoutManager as GridLayoutManager).spanCount
            if (leftItems == 0) {
                leftItems = (parent.layoutManager as GridLayoutManager).spanCount
            }
            childCount -= leftItems
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + child.translationY.roundToInt()
            val top = bottom - (mDivider?.intrinsicHeight ?: 0)
            mDivider?.setBounds(left + horizontalOffset, top, right - horizontalOffset, bottom)
            mDivider?.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            childCount = (parent.layoutManager as GridLayoutManager).spanCount
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.layoutManager?.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + child.translationX.roundToInt()
            val left = right - (mDivider?.intrinsicWidth ?: 0)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider?.intrinsicHeight ?: 0)
        } else {
            outRect.set(0, 0, mDivider?.intrinsicWidth ?: 0, 0)
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
        const val ALL = 2

        private val TAG = "DividerItem"
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
