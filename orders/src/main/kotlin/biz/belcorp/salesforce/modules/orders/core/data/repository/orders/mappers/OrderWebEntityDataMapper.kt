package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers

import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.OrderWebEntity
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWeb


class OrderWebEntityDataMapper {

    fun parse(entity: OrderWebEntity): OrderWeb {

        val pojo = OrderWeb()

        pojo.orderId = entity.orderId
        pojo.code = entity.code
        pojo.orderAmount = entity.orderAmount
        pojo.orderStatus = entity.orderStatus
        pojo.locked = entity.locked
        pojo.orderAmountMinimun = entity.orderAmountMinimun
        pojo.source = entity.source
        pojo.fullName = entity.fullName
        pojo.firstName = entity.firstName
        pojo.secondName = entity.secondName
        pojo.firstLastName = entity.firstLastName
        pojo.secondLastName = entity.secondLastName
        pojo.campania = entity.campania
        pojo.consultorasId = entity.consultorasId
        pojo.defaultPhone = entity.defaultPhone
        pojo.discount = entity.discount
        pojo.totalAmount = entity.totalAmount
        pojo.saldoPendiente = entity.saldoPendiente

        return pojo
    }

    fun parse(list: List<OrderWebEntity>): List<OrderWeb> {
        return list.asSequence()
                .map { parse(it) }
                .toList()
    }

}
