package biz.belcorp.salesforce.modules.orders.features.results.model


class ResultadoItemPedidosWebModel {

    var consultorasId: Int = 0
    var orderId: Int = 0
    var code: String? = null
    var orderAmount: String? = null
    var orderStatus: String? = null
    var locked: Int = 0
    var orderAmountMinimun: Int = 0
    var source: String? = null
    var fullName: String? = null
    var firstName: String? = null
    var secondName: String? = null
    var firstLastName: String? = null
    var secondLastName: String? = null
    var campania: String? = null
    var position: Int = 0
    var selected: Int = 0
    var display: Int = 0
    var defaultPhone: String? = null
    var totalAmount: String? = null
    var discount: String? = null
    var estado: String? = null
    var motivoRechazo: String? = null
    var saldoPendiente: String? = null

    companion object {

        const val ORIGEN_WEB = "W"
        const val ORIGEN_DIGITACION = "D"

    }

}
