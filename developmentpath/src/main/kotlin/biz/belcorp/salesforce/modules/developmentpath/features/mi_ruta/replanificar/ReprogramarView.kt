package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar

interface ReprogramarView {
    fun mostrarExito(fecha: String, hora: String)
    fun mostrarError(mensaje: String)
    fun notificarCambioEnPlanificacion()
}
