package biz.belcorp.salesforce.modules.inspires.util

import biz.belcorp.salesforce.modules.inspires.util.Constants.PREFIJO_CAMPANIA

fun String.enmascararACampaniaC() = if (this != "") PREFIJO_CAMPANIA + this.enmascararACampania() else ""

fun String.enmascararACampania() =
    if (this.length > 1) this.substring(this.length - 2)
    else this

fun String.obtenerAnioCampania() =
    if (this.length >= 4) this.substring(0, 4).toIntOrNull()
    else null
