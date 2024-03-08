package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model

import com.github.mikephil.charting.data.BarEntry

abstract class GraficoHistoricoBase(
    val margenesEnY: List<Float>,
    val barEntries: List<BarEntry>,
    val campanias: List<String>,
    val simboloALaIzquierda: String,
    val simboloALaDerecha: String,
    val colorBarras: Int,
    val titulo: String,
    val tituloGrafico: String,
    val subtitulo: String
)
