package biz.belcorp.salesforce.core.utils

import android.graphics.Color
import androidx.annotation.ColorInt

fun isColorLight(@ColorInt color: Int): Boolean {
    if (color == Color.BLACK)
        return false
    else if (color == Color.WHITE)
        return true
    val darkness = 1 - (0.299 * Color.red(color) +
        0.587 * Color.green(color) +
        0.114 * Color.blue(color)) / 255
    return darkness < 0.5
}
