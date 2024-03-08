package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers

import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.PedidoWebResponse
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult


class PedidoResponseDataMapper(private val orderWebMapper: OrderWebEntityDataMapper) {

    fun parse(response: PedidoWebResponse.PedidoWeb): OrderWebResult {

        val result = OrderWebResult()

        result.totalPedidos = response.totalPedidos
        result.totalPedidosBajoMontoMinimo = response.totalPedidosBajoMontoMinimo
        result.orderWebList = orderWebMapper.parse(response.orderWebList ?: emptyList())

        return result
    }

}
