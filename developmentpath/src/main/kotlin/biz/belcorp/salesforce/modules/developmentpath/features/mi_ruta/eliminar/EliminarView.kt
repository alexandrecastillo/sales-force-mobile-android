package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar

interface EliminarView {
    fun mostrarExito()
    fun mostrarError(mensaje: String)
    fun notificarCambioPlan()
}
