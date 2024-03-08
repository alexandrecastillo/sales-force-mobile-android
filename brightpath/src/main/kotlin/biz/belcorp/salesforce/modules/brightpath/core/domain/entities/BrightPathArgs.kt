package biz.belcorp.salesforce.modules.brightpath.core.domain.entities


sealed class BrightPathArgs: IndicatorArgs() {

    class Se(
             val campaniaAnterior: String,
             val acumuladoPorNivel: String,
             val periodoActual: String,
             val totalCampaniaAnterior: String,
             val acumuladoPeriodo: String) : BrightPathArgs()
}
