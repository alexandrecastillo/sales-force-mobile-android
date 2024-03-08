package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model

import biz.belcorp.mobile.components.charts.bar.model.BarEntry

class DatosGraficoHistorico(
    val valores: List<BarEntry>,
    val campanias: List<String>,
    val valoresVerticales: List<Float>,
    val enmascarado: Enmascarado,
    val colorBarras: Int,
    val titulo: String,
    val subtitulo: String,
    val mostrarCuadro: Boolean,
    val valoresEnCuadro: List<ValorXCampania>
) {

    class Enmascarado(
        val simboloALaDerecha: String,
        val simboloALaIzquierda: String
    )
}
