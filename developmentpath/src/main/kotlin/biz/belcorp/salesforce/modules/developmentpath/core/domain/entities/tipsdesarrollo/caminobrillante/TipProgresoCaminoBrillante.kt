package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.color.Color

class TipProgresoCaminoBrillante(
    val texto: String,
    val icono: TipoImagen,
    val colores: List<Color> = emptyList()
) {

    enum class TipoImagen(val valor: Int) {
        HITO(1),
        META(2),
        CONSTANCIA(3)
    }
}
