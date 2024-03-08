package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

class TopBottomHistorico(
    val tipoGrafico: TipoGrafico,
    val campania: String,
    val top: List<TopBottom>,
    val bottom: List<TopBottom>,
    val simboloAIzquierda: String?,
    val simboloADerecha: String?,
    val mostrarEnDosLineas: Boolean
) {
    enum class TipoTopBottom(val abreviatura: String) {
        TOP("T"),
        BOTTOM("B")
    }
}
