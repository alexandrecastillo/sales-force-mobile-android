package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.core.utils.ColorUtils

data class VentaGanancia(
    val ventaMonto: Valor? = null,
    val ventaDescripcion: Valor? = null,
    val gananciaMonto: Valor? = null,
    val gananciaDescripcion: Valor? = null
) {
    data class Valor(
        val texto: String? = null,
        val colores: List<Color> = emptyList()
    ) {
        val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }
    }
}
