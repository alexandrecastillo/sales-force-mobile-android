package biz.belcorp.salesforce.modules.postulants.features.widget.container

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class CustomHorizontalScrollview : HorizontalScrollView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onRequestFocusInDescendants(
        direction: Int,
        previouslyFocusedRect: Rect?
    ): Boolean {
        return true
    }

    override fun requestChildFocus(child: View, focused: View) {
        Constant.NOT_IMPLEMENTED
    }

    override fun pageScroll(direction: Int): Boolean {
        try {
            super.pageScroll(direction)
        } catch (ex: Exception) {
            Log.i("pageScroll", ex.message.orEmpty())
        }
        return true
    }

    override fun fullScroll(direction: Int): Boolean {
        super.fullScroll(direction)
        return true
    }

}
