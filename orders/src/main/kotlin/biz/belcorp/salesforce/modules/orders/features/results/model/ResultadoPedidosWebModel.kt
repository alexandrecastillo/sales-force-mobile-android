package biz.belcorp.salesforce.modules.orders.features.results.model


class ResultadoPedidosWebModel {

    var totalPedidos: Int = 0
    var totalPedidosBajoMontoMinimo: Int = 0
    var orderWebList: List<ResultadoItemPedidosWebModel> = emptyList()

}
