package biz.belcorp.salesforce.modules.developmentpath.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager

object ScreenUtil {
    @JvmStatic
    fun getScreenWidth(activity: Activity): Int {
        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        return size.x
    }

    @JvmStatic
    fun exceedScreenWidth(activity: Activity, vararg widths: Int): Boolean {
        var totalWidth = 0

        for (width in widths) {
            totalWidth += width
        }

        return totalWidth > getScreenWidth(activity) + 16
    }

    @JvmStatic
    fun convertPxToDp(context: Context, px: Float): Float {
        val resources = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
    }

    @JvmStatic
    fun convertDpToPx(context: Context, dp: Float): Int {
        val r = context.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.displayMetrics).toInt()
    }
}

fun Int.spToPx(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics).toInt()
}
