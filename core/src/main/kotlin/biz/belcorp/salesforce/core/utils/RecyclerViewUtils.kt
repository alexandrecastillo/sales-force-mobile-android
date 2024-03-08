package biz.belcorp.salesforce.core.utils

import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

fun RecyclerView.removeDecorations() {
    while (this.itemDecorationCount > NUMBER_ZERO) {
        this.removeItemDecorationAt(NUMBER_ZERO)
    }
}
