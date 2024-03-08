package biz.belcorp.salesforce.modules.developmentpath.features.flujo

interface FlujoMiRutaView {
    fun mostrarMensaje(mensaje: String)
    fun mostrarErrorConexion()
    fun irARuta(planId: Long)
    fun mostrarCargando()
    fun ocultarCargando()
}
