package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.utils.getTypeFace
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import biz.belcorp.salesforce.modules.developmentpath.utils.barConfigSinEspaciado
import biz.belcorp.salesforce.modules.developmentpath.utils.configuracionDataColors
import biz.belcorp.salesforce.modules.developmentpath.utils.graficoBaseSinColor
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import biz.belcorp.salesforce.core.R as CoreR

class RankingChartUtil(private val context: Context) {

    private val mulishBoldFont by lazy { context.getTypeFace(CoreR.font.mulish_bold) }

    fun configurarLineChart(
        linechart: LineChart,
        data: List<ChartEntryModel>
    ) {

        if (data.isEmpty()) return

        val listCamp = mutableListOf<String>()
        val values = mutableListOf<Entry>()

        for (i in data.indices) {
            listCamp.add(data[i].caption)
            values.add(Entry(i.toFloat(), data[i].firstValue))
        }

        linechart.apply {
            setDrawGridBackground(false)
            description.isEnabled = false
            setTouchEnabled(false)
            isDragEnabled = false
            setScaleEnabled(false)
            setNoDataText("NO HAY INFORMACIÓN PARA MOSTRAR GRÁFICAS")
            setPinchZoom(false)
            legend.isEnabled = false
            axisRight.isEnabled = false
        }

        val formatter = IAxisValueFormatter { value, _ -> listCamp[value.toInt()] }
        val xAxis = linechart.xAxis
        xAxis.apply {
            removeAllLimitLines()
            granularity = 1f // minimum axis-step (interval) is 1
            valueFormatter = formatter
            gridColor = Color.WHITE
            position = XAxis.XAxisPosition.BOTTOM
            typeface = mulishBoldFont
            textSize = 12f
        }

        val leftAxis = linechart.axisLeft
        leftAxis.apply {
            removeAllLimitLines()
            setDrawZeroLine(false)
            axisLineColor = Color.WHITE
            gridColor = Color.WHITE
            textColor = Color.WHITE
            axisMaximum = 110f
            axisMinimum = 0f
            granularity = 3f
            labelCount = 2
            zeroLineColor = Color.GRAY
        }

        val m = LimitLine(100f)
        m.enableDashedLine(10f, 10f, 0f)
        m.lineColor = ContextCompat.getColor(context, R.color.mi_ruta_success)
        val m1 = LimitLine(50f)
        m1.enableDashedLine(10f, 10f, 0f)
        m1.lineColor = Color.GRAY
        leftAxis.addLimitLine(m)
        leftAxis.addLimitLine(m1)

        leftAxis.enableGridDashedLine(10f, 10f, 0f)

        val dataSet = LineDataSet(values, "Ventas") // add entries to dataset
        dataSet.color = ContextCompat.getColor(context, R.color.porcentaje_normal)
        dataSet.setDrawValues(false)
        dataSet.lineWidth = 2.2f
        dataSet.setDrawFilled(true)
        dataSet.setCircleColors(ContextCompat.getColor(context, R.color.porcentaje_normal))
        dataSet.circleRadius = 4.3f
        val drawable = ContextCompat.getDrawable(context, R.drawable.fill_grafico)
        dataSet.fillDrawable = drawable
        val lineData = LineData(dataSet)
        linechart.data = lineData
        linechart.animateX(2200)
        linechart.invalidate()
    }

    fun configurarBarChartVenta(
        barChart: BarChart,
        entryModel: List<ChartEntryModel>
    ) {

        if (entryModel.isEmpty()) return

        barChart.graficoBaseSinColor()

        val data = prepararDataVenta(entryModel)

        val set1 = BarDataSet(data.first, "")
        set1.configuracionDataColors(data.second)

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IAxisValueFormatter { _, _ -> "" }

        val dataSets = mutableListOf<IBarDataSet>()
        dataSets.add(set1)

        val barData = BarData(dataSets)
        barData.barConfigSinEspaciado()
        barData.setValueFormatter(NewValueFormatter.newInstance())

        barChart.data = barData
    }

    private fun prepararDataVenta(data: List<ChartEntryModel>): Pair<List<BarEntry>, List<Int>> {

        val values = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()

        data.forEachIndexed { index, rankingGraphicModel ->

            val x = index.toFloat().times(3)

            colors += Color.parseColor("#bbe4e1")
            values += BarEntry(x, rankingGraphicModel.secondValue)

            colors += Color.parseColor("#99c9ff")
            values += BarEntry(x + 1, rankingGraphicModel.firstValue)

            colors += Color.parseColor("#ffffff")
            values += BarEntry(x + 2, index.toFloat(), 0f)

        }

        return Pair(values, colors)
    }

}
