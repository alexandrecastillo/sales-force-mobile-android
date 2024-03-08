package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.core.utils.ColorUtils

data class Tip(
    val id: Long = 0,
    val descripcion: String? = null,
    val colores: List<Color> = emptyList(),
    val orden: Int = 0
) {
    val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }
}
