package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido

interface IntencionPedidoView {

    fun pintarCantidadIntencionPedido(cantidadIntencionPedido: String)
    fun pintarCampania(campania: String)
    fun mostrarCargando()
    fun ocultarCargando()
}
