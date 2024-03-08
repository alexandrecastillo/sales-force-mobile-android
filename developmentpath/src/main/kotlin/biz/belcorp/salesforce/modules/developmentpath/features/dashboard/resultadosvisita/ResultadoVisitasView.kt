package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita

interface ResultadoVisitasView {
    fun mostrarContenedor()
    fun ocultarContenedor()
    fun pintarCantidadFacturadas(facturadas: Int)
    fun pintarCantidadNoFacturadas(noFacturadas: Int)
    fun mostrarBotonVerFacturadas()
    fun ocultarBotonVerFacturadas()
    fun mostrarFacturadas()
    fun ocultarFacturadas()
    fun mostrarNoFacturadas()
    fun ocultarNoFacturadas()
    fun mostrarFelicitaciones()
    fun ocultarFelicitaciones()
}
