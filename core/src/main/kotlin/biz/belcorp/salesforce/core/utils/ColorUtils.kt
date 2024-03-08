package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.domain.entities.color.Color

object ColorUtils {

    fun crearTipoColor(color: Int): Color {
        return when (color) {
            Color.NEGRO.valor -> Color.NEGRO
            Color.VERDE.valor -> Color.VERDE
            Color.ROJO.valor -> Color.ROJO
            Color.MAGENTA.valor -> Color.MAGENTA
            Color.DORADO.valor -> Color.DORADO
            else -> Color.NEGRO
        }
    }

    fun crearTipoColor(color: Color): Int {
        return when (color) {
            Color.NEGRO -> Color.NEGRO.valor
            Color.VERDE -> Color.VERDE.valor
            Color.ROJO -> Color.ROJO.valor
            Color.MAGENTA -> Color.MAGENTA.valor
            Color.DORADO -> Color.DORADO.valor
            else -> Color.NEGRO.valor
        }
    }
}
