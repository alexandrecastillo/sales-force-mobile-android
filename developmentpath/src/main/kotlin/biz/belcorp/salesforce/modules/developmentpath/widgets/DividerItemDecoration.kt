package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val mDivider: Drawable?,
    private val mShowFirstDivider: Boolean = false,
    private val mShowLastDivider: Boolean = false
) : RecyclerView.ItemDecoration() {

    private var mOrientation = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mDivider == null)
            return

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == 0 && !mShowFirstDivider) {
            return
        }

        if (mOrientation == -1)
            getOrientation(parent)

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.top = mDivider.intrinsicHeight
            if (mShowLastDivider && position == state.itemCount - 1) {
                outRect.bottom = outRect.top
            }
        } else {
            outRect.left = mDivider.intrinsicWidth
            if (mShowLastDivider && position == state.itemCount - 1) {
                outRect.right = outRect.left
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state)
            return
        }

        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        val size: Int
        val orientation = if (mOrientation != -1) mOrientation else getOrientation(parent)
        val childCount = parent.childCount

        if (orientation == LinearLayoutManager.VERTICAL) {
            size = mDivider.intrinsicHeight
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
        } else {
            size = mDivider.intrinsicWidth
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
        }

        val dividerParams = DividerParams(
            parent, state, c, orientation, left, right, top, bottom, size
        )

        for (i in (if (mShowFirstDivider) 0 else 1) until childCount) {
            dividerParams.child = parent.getChildAt(i)
            drawFirstDivider(dividerParams)
        }

        if (mShowLastDivider && childCount > 0) {
            dividerParams.child = parent.getChildAt(childCount - 1)
            drawLastDivider(dividerParams)
        }

    }

    private fun drawFirstDivider(dividerParams: DividerParams) = with(dividerParams) {
        val params = child.layoutParams as RecyclerView.LayoutParams
        if (orientation == LinearLayoutManager.VERTICAL) {
            top = child.top - params.topMargin - size
            bottom = top + size
        } else {
            left = child.left - params.leftMargin
            right = left + size
        }
        mDivider?.setBounds(left, top, right, bottom)
        mDivider?.draw(c)
    }

    private fun drawLastDivider(dividerParams: DividerParams) = with(dividerParams) {
        if (parent.getChildAdapterPosition(child) == state.itemCount - 1) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.bottom + params.bottomMargin
                bottom = top + size
            } else {
                left = child.right + params.rightMargin
                right = left + size
            }
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (mOrientation == -1) {
            if (parent.layoutManager is LinearLayoutManager) {
                val layoutManager = parent.layoutManager as LinearLayoutManager?
                mOrientation = layoutManager!!.orientation
            } else {
                throw IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager."
                )
            }
        }
        return mOrientation
    }

    data class DividerParams(
        val parent: RecyclerView,
        val state: RecyclerView.State,
        val c: Canvas,
        val orientation: Int,
        var left: Int,
        var right: Int,
        var top: Int,
        var bottom: Int,
        val size: Int
    ) {

        lateinit var child: View

    }

}
