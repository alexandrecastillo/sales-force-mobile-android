package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.core.utils.ColorUtils

class TipOferta<T>(
    val id: Long = 0L,
    val personaId: Long = 0L,
    val region: String = "-",
    val zona: String = "-",
    val seccion: String = "-",
    val descripcion: String? = "",
    val colores: List<Color> = emptyList(),
    val orden: Int = -1,
    val data: T
) {
    val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }
}
