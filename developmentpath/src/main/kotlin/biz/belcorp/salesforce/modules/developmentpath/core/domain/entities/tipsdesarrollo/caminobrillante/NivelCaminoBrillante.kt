package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante

class NivelCaminoBrillante(
    val id: Int,
    val titulo: String,
    val orden: Int
) {
    var esNivelActual: Boolean = false
    var esActiva: Boolean = false
}
