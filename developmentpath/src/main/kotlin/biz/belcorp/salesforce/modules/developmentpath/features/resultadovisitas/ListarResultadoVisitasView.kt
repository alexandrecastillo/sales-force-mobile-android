package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

interface ListarResultadoVisitasView {
    fun mostrarCampania(campania: String)
    fun mostrarTituloFacturaron()
    fun mostrarTituloNoFacturaron()
    fun mostrarSubtituloFacturaron(campania: String)
    fun mostrarSubtituloNoFacturaron(campania: String)
    fun mostrarCantidadTotalDeConsultoras(cantidad: Int)
    fun mostrarConsultoras(modelos: List<ConsultoraModel>, campaniaAnteriorNombreCorto: String)
    fun pintarCantidadFiltradas(cantidad: Int)
    fun mostrarCantidadFiltradas()
    fun ocultarCantidadFiltradas()
    fun mostrarSugerencias(sugerencias: List<String>)
    fun mostrarSinResultadosBusqueda()
    fun ocultarSinResultadosBusqueda()
    fun bloquearSwitch()
    fun desbloquearSwitch()
    fun mostrarCargando()
    fun ocultarCargado()
    fun resetearSwitch()
    fun mostrarErrorSwitch()
}
