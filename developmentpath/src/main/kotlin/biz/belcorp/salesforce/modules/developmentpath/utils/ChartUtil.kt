package biz.belcorp.salesforce.modules.developmentpath.utils

import android.graphics.Color
import android.graphics.Typeface
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.BarCharRendererCustom
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet

fun BarChart.graficoBaseSinColor() {
    setDrawBarShadow(false)
    configurarBase()
    xAxis.configuracionEjeX()
    axisLeft.configurarcionPositivoEjeY()
}

fun BarChart.graficoBaseConColorGris() {
    setDrawBarShadow(true)
    configurarBase()
    xAxis.configuracionEjeX()
    axisLeft.configurarcionPositivoEjeY()
}

fun BarChart.graficoBaseConColorGrisSinYminim() {
    setDrawBarShadow(true)
    configurarBase()
    xAxis.configuracionEjeX()
    axisLeft.configuracionEjeY()
}

fun BarChart.configurarBase() {
    setDrawValueAboveBar(true)
    description.isEnabled = false
    setDrawGridBackground(false)
    inhabilitarGestos()
    setNoDataText("NO HAY INFORMACIÓN PARA MOSTRAR GRÁFICAS")
    axisRight.isEnabled = false
    legend.isEnabled = false
    setFitBars(true)
    val customRenderer = BarCharRendererCustom(
            this,
            this.animator,
            this.viewPortHandler,
            25f)
    this.renderer = customRenderer
    invalidate()
}

fun BarChart.deshabilitarDescripcion() {
    description.isEnabled = false
}

fun BarChart.inhabilitarGestos() {
    isDragEnabled = false
    setPinchZoom(false)
    setTouchEnabled(false)
    setScaleEnabled(false)
}

fun YAxis.configuracionEjeY() {
    removeAllLimitLines()
    setDrawZeroLine(false)
    setDrawGridLines(false)
    setDrawAxisLine(false)
    setDrawLabels(false) //no se nuestra los valores de rango del Eje Y
    granularity = 3f
    labelCount = 2
    val m = LimitLine(0f)
    m.enableDashedLine(0f, 0f, 0f)
    m.lineColor = Color.parseColor("#eaeaec")
    m.lineWidth = 2f
    addLimitLine(m)
    enableGridDashedLine(10f, 10f, 0f)
}

fun BarChart.limpiarGrids() {
    axisRight.isEnabled = false
    legend.isEnabled = false
    setDrawGridBackground(false)
    xAxis.limpiarGrids()
    axisLeft.limpiarGrids()
}

fun BarChart.limpiarEjeIzquierdo() {
    axisLeft.limpiarEje()
}

fun BarChart.deshabilitarEjeIzquierdo() {
    axisLeft.isEnabled = false
}

fun BarChart.establecerCantidadEnY(cantidad: Int) {
    axisLeft.setLabelCount(cantidad, true)
}

fun BarChart.establecerMaximoEnY(valor: Float) {
    axisLeft.axisMaximum = valor
}

fun BarChart.establecerMinimoEnY(valor: Float) {
    axisLeft.axisMinimum = valor
}

fun BarChart.establecerEjeAbajo() {
    xAxis.establecerEjeAbajo()
}

fun BarChart.margenInferior(valor: Float) {
    extraBottomOffset = valor
}

fun BarChart.dibujarLineaLimiteInferior(color: Int) {
    axisLeft.dibujarLineaLimite(color = color)
}

fun YAxis.dibujarLineaLimite(grosor: Float = 2f, color: Int) {
    setDrawZeroLine(true)
    zeroLineWidth = grosor
    zeroLineColor = color
    val limitLine = LimitLine(4f)
    limitLine.lineWidth = grosor
    limitLine.lineColor = color
    addLimitLine(limitLine)
}

fun AxisBase.limpiarGrids() {
    removeAllLimitLines()
    setDrawGridLines(false)
}

fun AxisBase.limpiarEje() {
    setDrawAxisLine(false)
}

fun XAxis.establecerEjeAbajo() {
    position = XAxis.XAxisPosition.BOTTOM
}

fun XAxis.configuracionEjeX() {
    setDrawGridLines(false)
    setDrawAxisLine(false)
    granularity = 1f // minimum axis-step (interval) is 1
    gridColor = Color.WHITE
    position = XAxis.XAxisPosition.BOTTOM
    textSize = 11f
}

fun XAxis.setFontFamily(fontFamily: Typeface) {
    typeface = fontFamily
}

fun BarDataSet.configuracionDataColors(colors: List<Int>) {
    barSinIconoTexto()
    setDrawIcons(false)
    setDrawValues(false)
    this.colors = colors
    this.barShadowColor = Color.parseColor("#f4f4f4")
}

fun BarDataSet.colorearBarras(color: Int) {
    configDataColor(color)
}

fun BarDataSet.configDataColor(color: Int) {
    barSinIconoTexto()
    setDrawIcons(false)
    setDrawValues(false)
    this.color = color
    this.barShadowColor = Color.parseColor("#f4f4f4")
}

fun YAxis.configurarcionPositivoEjeY() {
    configuracionEjeY()
    axisMinimum = 0f
}

fun BarData.barConfigConEspaciado() {
    setValueTextSize(12f)
    barWidth = 0.30f
}

fun BarData.configurarAnchoBarra(ancho: Float) {
    barWidth = ancho
}

fun BarData.barConfigSinEspaciado() {
    setValueTextSize(12f)
    barWidth = 0.9f
}

fun BarChart.setFontFamily(fontFamily: Typeface) {
    xAxis.setFontFamily(fontFamily)
}

fun BarDataSet.barSinIconoTexto() {
    setDrawIcons(false)
    setDrawValues(false)
}
