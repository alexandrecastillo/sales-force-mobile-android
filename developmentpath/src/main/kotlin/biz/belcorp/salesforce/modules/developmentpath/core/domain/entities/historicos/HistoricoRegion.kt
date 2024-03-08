package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico

class HistoricoRegion(
    val tipoGrafico: TipoGrafico,
    val titulo: String,
    val tituloGrafico: String,
    val subtitulo: String,
    val simboloADerecha: String,
    val simboloAIzquierda: String,
    val campaniasValoresFdVs: List<CampaniaValorFdV>,
    val mostrarValoresEnCuadro: Boolean
) {
    class CampaniaValorFdV(val campania: String, val valor: Float, val fdv: Int)
}
