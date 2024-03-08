package biz.belcorp.salesforce.modules.orders.features.results


import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel


interface PedidosWebView {

    fun ocultarLoading()
    fun mostrarLoading()
    fun ocultarResultados()
    fun mostrarResultadosEncontrados()
    fun mostrarResultadosNoEncontrados()
    fun mostrarTotalResultados(total: Int)
    fun mostrarTotalResultadosMontoMinimo(totalMontoMinimo: Int)
    fun mostrarResultados(resultados: List<ResultadoItemPedidosWebModel>)
    fun configurarAdapter(campania: String, rol: Rol, bloqueoActivado: Int)
    fun mostrarCampaniaVenta(nombreCorto: String)
    fun mostrarCampaniaFacturacion(nombreCorto: String)
    fun mostrarBloqueoDialog(f: () -> Unit)
    fun mostrarDesloqueoDialog(f: () -> Unit)
    fun actualizarBloqueo(pedidoId: Int, bloqueado: Int)
    fun mostrarDialogoError()
    fun mostrarMensajeError(mensaje: String)

}
