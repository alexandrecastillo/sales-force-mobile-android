package biz.belcorp.salesforce.base.features.utils.decorations

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpaceGridInsideDecoration(
    context: Context,
    private val spanCount: Int,
    @DimenRes spacingHorizontalRes: Int,
    @DimenRes spacingVerticalRes: Int = spacingHorizontalRes
) : RecyclerView.ItemDecoration() {

    private val spacingHorizontal: Int = context.resources.getDimensionPixelSize(spacingHorizontalRes)
    private val spacingVertical: Int = context.resources.getDimensionPixelSize(spacingVerticalRes)
    private var needLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth = ((parent.width - spacingHorizontal.toFloat() * (spanCount - 1)) / spanCount).toInt()
        val padding = parent.width / spanCount - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.top = if (itemPosition < spanCount) 0 else spacingVertical
        outRect.bottom = 0

        if (itemPosition % spanCount == 0) {
            outRect.left = 0
            outRect.right = padding
            needLeftSpacing = true
        } else if ((itemPosition + 1) % spanCount == 0) {
            needLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (needLeftSpacing) {
            needLeftSpacing = false
            outRect.left = spacingHorizontal - padding
            if ((itemPosition + 2) % spanCount == 0) {
                outRect.right = spacingHorizontal - padding
            } else {
                outRect.right = spacingHorizontal / 2
            }
        } else if ((itemPosition + 2) % spanCount == 0) {
            needLeftSpacing = false
            outRect.left = spacingHorizontal / 2
            outRect.right = spacingHorizontal - padding
        } else {
            needLeftSpacing = false
            outRect.left = spacingHorizontal / 2
            outRect.right = spacingHorizontal / 2
        }
    }
}
