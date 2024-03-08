package biz.belcorp.salesforce.components.utils

import android.graphics.drawable.ShapeDrawable
import biz.belcorp.salesforce.components.widgets.TextDrawable

fun textToRoundDrawable(text: String, width: Int = 40, height: Int = 40, color: Int):
    ShapeDrawable {
    return TextDrawable
        .builder()
        .beginConfig()
        .width(width)
        .height(height)
        .fontSize(16)
        .endConfig()
        .buildRound(text, color)
}
