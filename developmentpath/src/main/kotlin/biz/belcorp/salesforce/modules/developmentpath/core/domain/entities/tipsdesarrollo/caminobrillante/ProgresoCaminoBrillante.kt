package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante

class ProgresoCaminoBrillante(
    val niveles: List<NivelCaminoBrillante> = emptyList(),
    val progreso: NivelActualCaminoBrillante
) {
    val nivelActual get() = niveles.firstOrNull { it.esNivelActual }
}
