package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoBase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoConCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoSinCuadro
import biz.belcorp.salesforce.modules.developmentpath.utils.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import biz.belcorp.salesforce.base.R as BaseR

class ConfiguradorGraficoBarras(
    private val context: Context,
    private val graficoView: BarChart
) {

    fun configurarSinCuadro(datos: GraficoHistoricoSinCuadro) {
        configurarBase(datos)
        configurarMargenes(datos.margenesEnY)
        graficoView.formatearEjeY(datos)
        graficoView.formatearEjeXCampaniaValor(datos)
        refrescar()
    }

    fun configurarConCuadro(datos: GraficoHistoricoConCuadro) {
        configurarBase(datos)
        configurarMargenes(datos.margenesEnY)
        graficoView.deshabilitarEjeIzquierdo()
        graficoView.formatearEjeXCampania(datos)
        refrescar()
    }

    private fun configurarBase(datos: GraficoHistoricoBase) {
        val dataSet = BarDataSet(datos.barEntries, null)
            .also { it.colorearBarras(ContextCompat.getColor(context, datos.colorBarras)) }
        customizarRender()
        configurarData(dataSet)
        graficoView.configurar()
    }

    private val mulishBoldFont by lazy {
        ResourcesCompat.getFont(context, BaseR.font.mulish_bold)
    }

    private fun configurarData(dataset: BarDataSet) {
        val barData = BarData(dataset)
        barData.configurarAnchoBarra(0.32f)
        graficoView.data = barData
    }

    private fun BarChart.configurar() {
        deshabilitarDescripcion()
        inhabilitarGestos()
        establecerEjeAbajo()
        mulishBoldFont?.let { setFontFamily(it) }
        margenInferior(30f)
        limpiarGrids()
        limpiarEjeIzquierdo()
        dibujarLineaLimiteInferior(ContextCompat.getColor(context ?: return, R.color.gris_escala_2))
    }

    private fun BarChart.formatearEjeXCampaniaValor(datos: GraficoHistoricoSinCuadro) {
        xAxis.valueFormatter = BaseFormatter.EjeXFormatter(
            campanias = datos.campanias,
            valoresEnY = datos.valoresEnY,
            simboloALaDerecha = datos.simboloALaDerecha,
            simboloALaIzquierda = datos.simboloALaIzquierda
        )

        setXAxisRenderer(
            BaseFormatter.MultilineaEjeXRendered(
                viewPortHandler,
                xAxis,
                getTransformer(YAxis.AxisDependency.LEFT)
            )
        )
    }

    private fun BarChart.formatearEjeXCampania(datos: GraficoHistoricoConCuadro) {
        xAxis.valueFormatter = BaseFormatter.EjeXFormatterSoloCampania(datos.campanias)
    }

    private fun BarChart.formatearEjeY(datos: GraficoHistoricoSinCuadro) {
        axisLeft.valueFormatter = BaseFormatter.EjeYFormatter(
            simboloALaIzquierda = datos.simboloALaIzquierda,
            simboloALaDerecha = datos.simboloALaDerecha
        )
    }

    private fun customizarRender() {
        val customRenderer = BarCharRendererCustom(
            graficoView,
            graficoView.animator,
            graficoView.viewPortHandler,
            20f
        )
        graficoView.renderer = customRenderer
    }

    private fun configurarMargenes(margenesEnY: List<Float>) {
        with(graficoView) {
            establecerCantidadEnY(3)
            establecerMaximoEnY(margenesEnY.max() ?: 0f)
            establecerMinimoEnY(0f)
        }
    }

    private fun refrescar() {
        graficoView.invalidate()
    }
}
