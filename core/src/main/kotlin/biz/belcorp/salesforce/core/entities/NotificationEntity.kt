package biz.belcorp.salesforce.core.entities

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class NotificationEntity(
    @Id(assignable = true) var id: Long = 0,
    var code: Long = 0,
    var topic: String = Constant.EMPTY_STRING,
    var title: String = Constant.EMPTY_STRING,
    var message: String = Constant.EMPTY_STRING,
    var data: String = Constant.EMPTY_STRING,
    var date: Long = Constant.NUMBER_ZERO.toLong(),
    var seen: Boolean = false
)
