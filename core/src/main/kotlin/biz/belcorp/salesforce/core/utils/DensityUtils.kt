package biz.belcorp.salesforce.core.utils

import android.util.DisplayMetrics.*

const val DEFAULT_DENSITY: String = "xxxhdpi"

fun Int.asDensity(): String? {
    return when (this) {
        DENSITY_LOW -> "ldpi"
        DENSITY_MEDIUM -> "mdpi"
        DENSITY_HIGH -> "hdpi"
        DENSITY_XHIGH -> "xhdpi"
        DENSITY_XXHIGH -> "xxhdpi"
        DENSITY_XXXHIGH -> "xxxhdpi"
        else -> DEFAULT_DENSITY
    }
}
