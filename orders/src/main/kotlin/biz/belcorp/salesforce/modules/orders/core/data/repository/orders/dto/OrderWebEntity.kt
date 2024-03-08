package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto

import com.google.gson.annotations.SerializedName

class OrderWebEntity {

    @SerializedName("consultorasId")
    var consultorasId: Int = 0

    @SerializedName("pedidoID")
    var orderId: Int = 0

    @SerializedName("codigo")
    var code: String? = null

    @SerializedName("estadoEnvio")
    var orderStatus: String? = null

    @SerializedName("bloqueado")
    var locked: Int = 0

    @SerializedName("flagMontoMinimo")
    var orderAmountMinimun: Int = 0

    @SerializedName("origen")
    var source: String? = null

    @SerializedName("nombreCompleto")
    var fullName: String? = null

    @SerializedName("primerNombre")
    var firstName: String? = null

    @SerializedName("segundoNombre")
    var secondName: String? = null

    @SerializedName("primerApellido")
    var firstLastName: String? = null

    @SerializedName("segundoApellido")
    var secondLastName: String? = null

    @SerializedName("campania")
    var campania: String? = null

    @SerializedName("telefonoDefault")
    var defaultPhone: String? = null

    @SerializedName("montoTotal")
    var totalAmount: String? = null

    @SerializedName("descuentoProl")
    var discount: String? = null

    @SerializedName("montoPedido")
    var orderAmount: String? = null

    @SerializedName("saldoPendiente")
    var saldoPendiente: String? = null

}
