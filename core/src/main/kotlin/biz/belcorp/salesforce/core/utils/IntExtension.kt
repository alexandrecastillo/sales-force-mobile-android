package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant

fun Int.porcentajeDe(denominador: Int): Int {
    return if (denominador != Constant.NUMERO_CERO) {
        ((this.toDouble() / denominador.toDouble()) * Constant.NUMERO_CIEN).toInt()
    } else {
        Constant.NUMERO_CERO
    }
}

fun Float.aproximarASiguienteDecena(): Float {
    val residuo = rem(Constant.NUMBER_TEN)
    val cantidadRestanteParaDiez = Constant.NUMBER_TEN - residuo
    return this + cantidadRestanteParaDiez
}
