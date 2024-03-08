package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class NivelActualCaminoBrillante(
    val llaveUA: LlaveUA,
    val nivelActual: Int,
    val progreso: Long,
    val hito: Long,
    val meta: Long,
    val tips: List<TipProgresoCaminoBrillante>
) {
    val esNivelValido get() = nivelActual > 0
}
