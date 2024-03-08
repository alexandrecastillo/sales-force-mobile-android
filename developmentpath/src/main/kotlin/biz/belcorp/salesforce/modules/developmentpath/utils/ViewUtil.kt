package biz.belcorp.salesforce.modules.developmentpath.utils

import android.view.View

fun View.cambiarVisibilidad() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

fun View?.invertirVisibilidad() {
    if (this == null) return
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}
