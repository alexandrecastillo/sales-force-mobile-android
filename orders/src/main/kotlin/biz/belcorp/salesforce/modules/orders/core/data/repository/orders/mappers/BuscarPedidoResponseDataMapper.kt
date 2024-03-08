package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.core.utils.separarNombre
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BuscarPedidoResponse
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWeb
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult


class BuscarPedidoResponseDataMapper {

    fun parse(response: BuscarPedidoResponse): OrderWebResult {

        val result = OrderWebResult()

        result.totalPedidos = response.resultado.size
        result.totalPedidosBajoMontoMinimo = response.resultado.firstOrNull()?.montoPedidoFacturado?.toInt()
            ?: NUMERO_CERO
        result.orderWebList = response.resultado.map { parsePedido(it) }

        return result
    }

    private fun parsePedido(pedido: BuscarPedidoResponse.Pedido): OrderWeb {

        val orderWeb = OrderWeb()

        orderWeb.campania = pedido.campaniaIngreso
        orderWeb.consultorasId = pedido.consultoraID ?: NUMERO_CERO
        orderWeb.defaultPhone = pedido.telefonoCelular ?: pedido.telefonoCasa
        orderWeb.locked = NUMERO_CERO
        orderWeb.fullName = pedido.nombre

        val nombres = pedido.nombre.orEmpty().separarNombre()
        orderWeb.firstName = nombres.first
        orderWeb.secondName = EMPTY_STRING
        orderWeb.firstLastName = nombres.second
        orderWeb.secondLastName = nombres.third

        orderWeb.totalAmount = pedido.montoPedidoFacturado.toString()
        orderWeb.orderAmount = pedido.ventaConsultora.toString()
        orderWeb.code = pedido.codigodeConsultora
        orderWeb.discount = EMPTY_STRING
        orderWeb.orderAmountMinimun = NUMERO_CERO
        orderWeb.orderStatus = EMPTY_STRING
        orderWeb.source = pedido.origenPedido?.guionSiVacioONull()
        orderWeb.orderId = NUMERO_CERO
        orderWeb.estado = pedido.estado
        orderWeb.motivoRechazo = pedido.motivoRechazo
        orderWeb.saldoPendiente = pedido.saldoPendienteTotal

        return orderWeb
    }

}
