package biz.belcorp.salesforce.core.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layout: Int) =
    LayoutInflater.from(context).inflate(layout, this, false)

fun ViewGroup.inflate(layoutInflater: LayoutInflater, @LayoutRes layout: Int) =
    layoutInflater.inflate(layout, this, false)

fun ViewGroup.onKeyboardVisibilityChanged(onOpen: () -> Unit, onClose: () -> Unit) {
    val parentView = getChildAt(0)
    parentView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {

        private var alreadyOpen: Boolean = false
        private val defaultKeyboardHeightDP = 100
        private val estimatedKeyboardDP =
            defaultKeyboardHeightDP + if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) 48 else 0
        private val rect = android.graphics.Rect()

        override fun onGlobalLayout() {
            val estimatedKeyboardHeight = android.util.TypedValue.applyDimension(
                android.util.TypedValue.COMPLEX_UNIT_DIP,
                estimatedKeyboardDP.toFloat(),
                parentView.resources.displayMetrics
            ).toInt()
            parentView.getWindowVisibleDisplayFrame(rect)
            val heightDiff = parentView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight

            if (isShown == alreadyOpen) {
                return
            }
            alreadyOpen = isShown
            if (isShown) {
                onOpen.invoke()
            } else {
                onClose.invoke()
            }
        }
    })
}
