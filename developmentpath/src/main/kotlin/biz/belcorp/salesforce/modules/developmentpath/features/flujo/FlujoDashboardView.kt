package biz.belcorp.salesforce.modules.developmentpath.features.flujo

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface FlujoDashboardView {
    fun irADashboardGr(planId: Long)
    fun irADashboardGz(planId: Long)
    fun irADashboardSe(planId: Long)
    fun irADashboardDv(planId: Long)
    fun mostrarMensaje(mensaje: String)
    fun mostrarErrorConexion()
    fun mostrarErrorPlanInvalido(rolAsociado: Rol)
    fun mostrarCargando()
    fun ocultarCargando()
    fun notificarCambioRDD()
}
