package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto

import com.google.gson.annotations.SerializedName

class PedidoWebResponse {

    @SerializedName("resultado")
    val resultado: PedidoWeb? = null

    class PedidoWeb {

        @SerializedName("totalPedidos")
        var totalPedidos: Int = 0

        @SerializedName("totalPedidosBajoMontoMinimo")
        var totalPedidosBajoMontoMinimo: Int = 0

        @SerializedName("pedidosList")
        var orderWebList: List<OrderWebEntity>? = null

    }

}
