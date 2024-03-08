package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar

interface PlanificarRapidoView {
    fun pintarNombrePersona(nombre: String)
    fun cerrar()
    fun mostrarError(mensaje: String)
    fun notificarExitoPlanificacion()
    fun notificarCambioPlan()
}
