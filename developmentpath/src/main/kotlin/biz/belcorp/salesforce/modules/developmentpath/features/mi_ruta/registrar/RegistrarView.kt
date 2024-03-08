package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

interface RegistrarView {
    fun habilitarBotonRegistro()
    fun deshabilitarBotonRegistro()
    fun mostrarExitoRegistro()
    fun mostrarError(mensaje: String)
    fun notificarCambioEnPlanificacion()
    fun alRecuperarIndicadores(realizadas: Int, planificadas: Int)
    fun cerrar()
}
