package biz.belcorp.salesforce.modules.orders.core.domain.entities.orders

class OrderWebResult {
    var totalPedidos: Int = 0
    var totalPedidosBajoMontoMinimo: Int = 0
    var orderWebList: List<OrderWeb> = emptyList()
}
