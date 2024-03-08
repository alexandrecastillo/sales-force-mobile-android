package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto

import com.google.gson.annotations.SerializedName

class BuscarPedidoResponse {

    @SerializedName("resultado")
    val resultado: List<Pedido> = emptyList()

    class Pedido {

        @SerializedName("consultoraID")
        var consultoraID: Int? = 0

        @SerializedName("nombre")
        var nombre: String? = ""

        @SerializedName("codigodeConsultora")
        var codigodeConsultora: String? = ""

        @SerializedName("territorio")
        var territorio: String? = ""

        @SerializedName("telefonoCasa")
        var telefonoCasa: String? = ""

        @SerializedName("telefonoCelular")
        var telefonoCelular: String? = ""

        @SerializedName("constancia")
        var constancia: String? = ""

        @SerializedName("segmentacion")
        var segmentacion: String? = ""

        @SerializedName("saldoPendienteTotal")
        var saldoPendienteTotal: String? = ""

        @SerializedName("ventaConsultora")
        var ventaConsultora: String? = ""

        @SerializedName("montoPedidoFacturado")
        var montoPedidoFacturado: Float? = 0f

        @SerializedName("motivoRechazo")
        var motivoRechazo: String? = ""

        @SerializedName("documentodeIdentidad")
        var documentodeIdentidad: String? = ""

        @SerializedName("direccion")
        var direccion: String? = ""

        @SerializedName("email")
        var email: String? = ""

        @SerializedName("familiaProtegida")
        var familiaProtegida: String? = ""

        @SerializedName("usaFlexipago")
        var usaFlexipago: String? = ""

        @SerializedName("esBrillante")
        var esBrillante: String? = ""

        @SerializedName("campaniaIngreso")
        var campaniaIngreso: String? = ""

        @SerializedName("ultimaFacturacion")
        var ultimaFacturacion: String? = ""

        @SerializedName("origenPedido")
        var origenPedido: String? = ""

        @SerializedName("cumpleanios")
        var cumpleanios: String? = ""

        @SerializedName("estado")
        var estado: String? = null

    }

}
