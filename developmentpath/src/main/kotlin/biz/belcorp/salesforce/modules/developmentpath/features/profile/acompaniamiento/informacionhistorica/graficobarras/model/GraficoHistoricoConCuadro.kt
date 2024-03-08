package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model

import com.github.mikephil.charting.data.BarEntry

class GraficoHistoricoConCuadro(
    val valoresXCampania: List<ValorXCampania>,
    margenesEnY: List<Float>,
    barEntries: List<BarEntry>,
    campanias: List<String>,
    simboloALaIzquierda: String,
    simboloALaDerecha: String,
    colorBarras: Int,
    titulo: String,
    subtitulo: String,
    tituloGrafico: String
) : GraficoHistoricoBase(
    margenesEnY = margenesEnY,
    barEntries = barEntries,
    campanias = campanias,
    simboloALaIzquierda = simboloALaIzquierda,
    simboloALaDerecha = simboloALaDerecha,
    colorBarras = colorBarras,
    titulo = titulo,
    subtitulo = subtitulo,
    tituloGrafico = tituloGrafico
)
