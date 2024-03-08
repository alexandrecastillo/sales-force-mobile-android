package biz.belcorp.salesforce.components.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(
    private val tf: Typeface,
    private val color: Int = SIN_COLOR
) : MetricAffectingSpan() {

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint, tf)
    }

    override fun updateDrawState(ds: TextPaint) {
        apply(ds, tf)
    }

    private fun apply(paint: Paint, tf: Typeface) {
        val oldStyle: Int

        val old = paint.typeface
        oldStyle = old?.style ?: 0

        val fake = oldStyle and tf.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = VAL_TEXT_SKEW_X
        }

        if (color != SIN_COLOR)
            paint.color = color

        paint.typeface = tf
    }

    companion object {
        const val SIN_COLOR = -1
        const val VAL_TEXT_SKEW_X = -0.25f
    }
}
