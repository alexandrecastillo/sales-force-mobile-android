package biz.belcorp.salesforce.core.utils

import android.view.View
import android.view.animation.*
import biz.belcorp.salesforce.core.constants.Constant

const val FROM_DEGREES = -20f
const val TO_DEGREES = 20f
const val ANIM_DEFAULT_TIME = 1000L

fun View.rotateAnimation(
    fromDegrees: Float = FROM_DEGREES,
    toDegrees: Float = TO_DEGREES,
    times: Int = Constant.NUMERO_UNO,
    repeatMode: Int = Animation.RESTART,
    duration: Long = ANIM_DEFAULT_TIME
) {
    val pivotValue = 0.5f
    val rotateAnimation = RotateAnimation(
        fromDegrees,
        toDegrees,
        Animation.RELATIVE_TO_SELF,
        pivotValue,
        Animation.RELATIVE_TO_SELF,
        pivotValue
    ).apply {
        this.duration = duration
        fillAfter = true
        repeatCount = times
        this.repeatMode = repeatMode
        interpolator = LinearInterpolator()
    }
    animation = rotateAnimation
}

fun View.fadeAnimation(
    fromAlpha: Float = 0.0f,
    toAlpha: Float = 1f,
    repeatCount: Int = Constant.NUMERO_UNO,
    repeatMode: Int = 0,
    duration: Long = 500
) {
    val alphaAnimation = AlphaAnimation(fromAlpha, toAlpha).apply {
        this.repeatCount = repeatCount
        this.repeatMode = repeatMode
        this.duration = duration
        interpolator = AccelerateInterpolator(1f)
    }
    animation = alphaAnimation
}
