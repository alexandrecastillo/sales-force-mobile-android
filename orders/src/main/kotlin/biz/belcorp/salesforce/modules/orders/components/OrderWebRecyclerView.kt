package biz.belcorp.salesforce.modules.orders.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class OrderWebRecyclerView : RecyclerView {

    private var isVerticleScrollingEnabled = true

    fun enableVersticleScroll(enabled: Boolean) {
        isVerticleScrollingEnabled = enabled
    }

    override fun computeVerticalScrollRange(): Int {
        return if (isVerticleScrollingEnabled) super.computeVerticalScrollRange() else 0
    }


    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        return isVerticleScrollingEnabled && super.onInterceptTouchEvent(e)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

}
