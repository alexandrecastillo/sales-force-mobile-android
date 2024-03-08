package biz.belcorp.salesforce.modules.orders.core.domain.entities.orders

import biz.belcorp.salesforce.core.utils.removeExtraWhiteSpaces

class OrderWeb {

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
    var defaultPhone: String? = null
    var totalAmount: String? = null
    var discount: String? = null
    var estado: String? = null
    var motivoRechazo: String? = null
    var saldoPendiente: String? = null

    fun invertirNombre() {
        fullName = "$firstLastName $secondLastName $firstName $secondName".removeExtraWhiteSpaces()
    }

}
