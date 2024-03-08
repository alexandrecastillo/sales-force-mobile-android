package biz.belcorp.salesforce.core.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setFont(@FontRes id: Int) {
    val typeface = ResourcesCompat.getFont(context, id)
    setTypeface(typeface)
}

fun TextView.setEmoji(emoji: String) {
    text = text.toString().plus(createEmoji(emoji))
}
