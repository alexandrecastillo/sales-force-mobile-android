package biz.belcorp.salesforce.modules.orders.features.results.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoPedidosWebModel


class ResultadoPedidosWebMapper(
    private val itemMapper: ResultadoItemPedidosWebMapper
) : Mapper<ResultadoPedidosWebModel, OrderWebResult>() {

    override fun map(value: ResultadoPedidosWebModel) = OrderWebResult()

    override fun reverseMap(value: OrderWebResult): ResultadoPedidosWebModel {
        return ResultadoPedidosWebModel().apply {
            totalPedidos = value.totalPedidos
            totalPedidosBajoMontoMinimo = value.totalPedidosBajoMontoMinimo
            orderWebList = itemMapper.reverseMap(value.orderWebList)
        }
    }
}
