package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model

import biz.belcorp.salesforce.core.utils.aproximarASiguienteDecena
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.ProveedorColorBarras
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.ProveedorFormato
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import com.github.mikephil.charting.data.BarEntry

class HistoricoMapper {

    private fun HistoricoRegion.extraerEntries(): List<BarEntry> {
        return campaniasValoresFdVs.mapIndexed { index: Int, valorFdV: HistoricoRegion.CampaniaValorFdV ->
            BarEntry(index * 1f, valorFdV.valor)
        }
    }

    private fun HistoricoRegion.extraerCampanias(): List<String> {
        return campaniasValoresFdVs.map { it.campania.maskCampaignWithPrefix() }
    }

    private fun HistoricoRegion.extraerMargenesEnY(): List<Float> {
        val valoresEnY = campaniasValoresFdVs.map { it.valor }
        val valorMinimo = 0f
        val valorMaximo = valoresEnY.max()?.aproximarASiguienteDecena() ?: 0f
        val valorMedio = (valorMinimo + valorMaximo) / 2
        return listOf(valorMinimo, valorMaximo, valorMedio)
    }

    private fun HistoricoRegion.extraerColor(): Int {
        val proveedorColor = ProveedorColorBarras()
        return proveedorColor.obtenerColor(tipoGrafico.codigo)
    }

    private fun HistoricoRegion.extraerValoresParaCuadro(): List<ValorXCampania> {
        val campanias = extraerCampanias()
        val valores = extraerValoresComoTexto()
        val fdvs = extraerFdVComoTexto()
        return if (campanias.size == valores.size && campanias.size == fdvs.size) {
            campanias.mapIndexed { index, campania ->
                ValorXCampania(
                    campania,
                    valores[index],
                    fdvs[index]
                )
            }
        } else {
            emptyList()
        }
    }

    private fun HistoricoRegion.obtenerMinimoEjeY(): Float {
        return 0f
    }

    private fun HistoricoRegion.obtenerMaximoEjeY(): Float {
        return (this.campaniasValoresFdVs.maxBy { it.valor }?.valor ?: 0f) * 1.10f
    }

    private fun HistoricoRegion.extraerValoresComoTexto(): List<String> {
        val formatter = ProveedorFormato()
        return extraerEntries()
            .map { formatter.obtenerFormato(tipoGrafico.codigo, it.y) }
            .map { "$simboloAIzquierda$it$simboloADerecha" }
    }

    private fun HistoricoRegion.extraerFdVComoTexto(): List<String> {
        return this.campaniasValoresFdVs.map { "${it.fdv}%" }
    }

    fun map(historico: HistoricoRegion): GraficoHistoricoBase {
        return if (historico.mostrarValoresEnCuadro)
            mapConCuadro(historico)
        else
            mapSinCuadro(historico)
    }

    private fun mapConCuadro(historicoRegion: HistoricoRegion): GraficoHistoricoConCuadro {
        return GraficoHistoricoConCuadro(
            valoresXCampania = historicoRegion.extraerValoresParaCuadro(),
            margenesEnY = historicoRegion.extraerMargenesEnY(),
            barEntries = historicoRegion.extraerEntries(),
            campanias = historicoRegion.extraerCampanias(),
            simboloALaIzquierda = historicoRegion.simboloAIzquierda,
            simboloALaDerecha = historicoRegion.simboloADerecha,
            colorBarras = historicoRegion.extraerColor(),
            titulo = historicoRegion.titulo,
            tituloGrafico = historicoRegion.tituloGrafico,
            subtitulo = historicoRegion.subtitulo
        )
    }

    private fun mapSinCuadro(historicoRegion: HistoricoRegion): GraficoHistoricoSinCuadro {
        return GraficoHistoricoSinCuadro(
            margenesEnY = historicoRegion.extraerMargenesEnY(),
            valoresEnY = obtenerValoresEnYFormateados(
                historicoRegion.tipoGrafico,
                historicoRegion.extraerEntries().map { it.y }),
            barEntries = historicoRegion.extraerEntries(),
            campanias = historicoRegion.extraerCampanias(),
            simboloALaIzquierda = historicoRegion.simboloAIzquierda,
            simboloALaDerecha = historicoRegion.simboloADerecha,
            colorBarras = historicoRegion.extraerColor(),
            titulo = historicoRegion.titulo,
            tituloGrafico = historicoRegion.tituloGrafico,
            subtitulo = historicoRegion.subtitulo
        )
    }

    private fun obtenerValoresEnYFormateados(
        tipoGrafico: TipoGrafico,
        valoresEnY: List<Float>
    ): List<String> {
        val proveedorFormato = ProveedorFormato()
        return valoresEnY.map { proveedorFormato.obtenerFormato(tipoGrafico.codigo, it) }
    }
}
