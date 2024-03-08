package biz.belcorp.salesforce.modules.kpis.utils

import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.core.view.setPadding
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.ValueAttributes
import com.google.android.material.textview.MaterialTextView

fun MaterialTextView.setAttrs(valueAttrs: ValueAttributes) = with(valueAttrs) {
    text = value
    setTextAppearance(context, textAppearanceInt)
}

fun MaterialTextView.changeTextSize(@DimenRes dimenRes: Int) {
    val resource = context.resources.getDimension(dimenRes)
    setTextSize(TypedValue.COMPLEX_UNIT_PX, resource)
}

fun MaterialTextView.resetLayoutParams(resId: Int) {
    val attributes = context.obtainStyledAttributes(resId, R.styleable.SimpleItemTextView)
    val width = attributes.getDimensionPixelSize(R.styleable.SimpleItemTextView_simpleItemWidth, NEGATIVE_NUMBER_ONE)
    val padding = attributes.getDimensionPixelSize(R.styleable.SimpleItemTextView_simpleItemPadding, NEGATIVE_NUMBER_ONE)
    attributes.recycle()
    if (width != NEGATIVE_NUMBER_ONE) layoutParams.width = width
    if (padding != NEGATIVE_NUMBER_ONE) setPadding(padding)
}
