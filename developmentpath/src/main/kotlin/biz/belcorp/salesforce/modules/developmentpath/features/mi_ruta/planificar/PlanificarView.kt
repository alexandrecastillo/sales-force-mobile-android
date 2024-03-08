package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar

interface PlanificarView {
    fun mostrarExito(fecha: String, hora: String)
    fun mostrarError(mensaje: String)
    fun notificarCambioPlan()
}
