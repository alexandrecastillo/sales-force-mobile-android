package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import android.graphics.Canvas
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class BaseFormatter {

    class EjeYFormatter(private val simboloALaDerecha: String,
                        private val simboloALaIzquierda: String): IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "$simboloALaIzquierda$value$simboloALaDerecha"
        }
    }

    class EjeXFormatter(private val campanias: List<String>,
                        private val valoresEnY: List<String>,
                        private val simboloALaDerecha: String,
                        private val simboloALaIzquierda: String): IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            val valor = "$simboloALaIzquierda${valoresEnY[value.toInt()]}$simboloALaDerecha"

            return "${campanias[value.toInt()]}\n$valor"
        }
    }

    class EjeXFormatterSoloCampania(private val campanias: List<String>): IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {

            return campanias[value.toInt()]
        }
    }

    class MultilineaEjeXRendered(viewPortHandler: ViewPortHandler, xAxis: XAxis, trans: Transformer) :
            XAxisRenderer(viewPortHandler, xAxis, trans) {

        override fun drawLabel(c: Canvas, formattedLabel: String, x: Float, y: Float, anchor: MPPointF, angleDegrees: Float) {
            val lineas = formattedLabel.split("\n".toRegex()).dropLastWhile { it.isEmpty() }

            lineas.forEachIndexed { index, texto ->
                val desplazamientoEnY = y + mAxisLabelPaint.textSize * index
                Utils.drawXAxisValue(c, texto, x, desplazamientoEnY, mAxisLabelPaint, anchor, angleDegrees)
            }
        }
    }
}
