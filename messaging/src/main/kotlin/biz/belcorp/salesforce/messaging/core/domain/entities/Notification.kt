package biz.belcorp.salesforce.messaging.core.domain.entities

import biz.belcorp.salesforce.core.constants.Constant

open class Notification {

    var id: Long = 0
    var topic: String = Constant.EMPTY_STRING
    var title: String = Constant.EMPTY_STRING
    var message: String = Constant.EMPTY_STRING
    var dataString: String = Constant.EMPTY_STRING
    var date: Long = Constant.NUMBER_ZERO.toLong()
    var seen: Boolean = false

}
