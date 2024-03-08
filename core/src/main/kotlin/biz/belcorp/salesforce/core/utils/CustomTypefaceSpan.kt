package biz.belcorp.salesforce.core.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(family: String, private val newType: Typeface, private val color: Int = SIN_COLOR) : TypefaceSpan(family) {

    companion object {
        const val SIN_COLOR = -1
    }

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType, color)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType, color)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface, color: Int) {
        val old = paint.typeface
        val oldStyle = old?.style ?: 0
        val fake = oldStyle and tf.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        if (color != SIN_COLOR)
            paint.color = color
        paint.typeface = tf
    }
}
