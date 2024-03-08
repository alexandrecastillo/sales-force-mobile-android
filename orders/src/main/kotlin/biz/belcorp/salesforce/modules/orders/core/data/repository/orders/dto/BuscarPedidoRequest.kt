package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BuscarPedidoRequest {

    @SerialName("TipoFiltro")
    var tipoFiltro: String? = null

    @SerialName("Pais")
    var pais: String? = null

    @SerialName("Campania")
    var campania: String? = null

    @SerialName("ConsultoraLiderID")
    var consultoraLiderId: String? = null

    @SerialName("Seccion")
    var seccion: String? = null

    @SerialName("EstadoPedido")
    var estadoPedido: Int? = null

    @SerialName("FechaDesde")
    var fechaDesde: String? = null

    @SerialName("FechaHasta")
    var fechaHasta: String? = null

    @SerialName("CodigoConsultora")
    var codigoConsultora: String? = null

    @SerialName("NombreConsultora")
    var nombreConsultora: String? = null

    @SerialName("SegmentoID")
    var segmentoId: Int? = null

    @SerialName("Deuda")
    var deuda: String? = null

}
