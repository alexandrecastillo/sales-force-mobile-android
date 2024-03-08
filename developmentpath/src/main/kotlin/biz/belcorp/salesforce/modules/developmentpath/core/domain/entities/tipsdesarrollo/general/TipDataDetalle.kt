package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general

import biz.belcorp.salesforce.core.domain.entities.color.Color

class TipDataDetalle(
    val texto: String,
    val colores: List<Color> = emptyList()
)
