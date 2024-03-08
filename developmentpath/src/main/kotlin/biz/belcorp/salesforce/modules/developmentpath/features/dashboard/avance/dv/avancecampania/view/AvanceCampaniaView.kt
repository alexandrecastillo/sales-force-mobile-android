package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.view

interface AvanceCampaniaView {
    fun pintarPorcentaje(avance: Float)
    fun pintarDiasRestantes(dias: Int)
    fun pintarHoyEsCierre()
    fun pintarPedidosFacturados(cantidad: String)
    fun pintarMetaFacturados(cantidad: String)
    fun pintarPorcentajeFacturados(porcentaje: Int)
    fun ocultarPorcentajeLogrado()
    fun pintarCantidadIntencionPedido(cantidadIntencionPedido: String)
}
