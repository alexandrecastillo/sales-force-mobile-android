package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoConCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoSinCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.ValorXCampania

interface GraficoResumenView {
    fun pintarGraficoConCuadro(datos: GraficoHistoricoConCuadro)
    fun pintarGraficoSinCuadro(datos: GraficoHistoricoSinCuadro)
    fun pintarTitulo(titulo: String)
    fun pintarTituloGrafico(titulo: String)
    fun pintarSubtitulo(subtitulo: String)
    fun dibujarIcono(iconoId: Int)
    fun mostrarGraficoTopBottom()
    fun mostrarGraficoCuadroProductivas()
    fun pintarBackgroundColor(color: Int)
    fun pintarValoresEnCuadro(valores: List<ValorXCampania>)
    fun pintarTituloEnCuadro(titulo: String)
}
