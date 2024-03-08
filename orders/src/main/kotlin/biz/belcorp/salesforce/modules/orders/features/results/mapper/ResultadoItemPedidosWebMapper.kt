package biz.belcorp.salesforce.modules.orders.features.results.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWeb
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel


class ResultadoItemPedidosWebMapper : Mapper<ResultadoItemPedidosWebModel, OrderWeb>() {

    override fun map(value: ResultadoItemPedidosWebModel) = OrderWeb()

    override fun reverseMap(value: OrderWeb): ResultadoItemPedidosWebModel {
        return ResultadoItemPedidosWebModel().apply {
            orderId = value.orderId
            code = value.code?.trim()
            orderAmount = value.orderAmount
            orderStatus = WordUtils.capitalizeFully(value.orderStatus)
            locked = value.locked
            orderAmountMinimun = value.orderAmountMinimun
            source = value.source
            fullName = WordUtils.capitalizeFully(value.fullName)
            firstName = value.firstName
            secondName = value.secondName
            firstLastName = value.firstLastName
            secondLastName = value.secondLastName
            campania = value.campania
            consultorasId = value.consultorasId
            defaultPhone = value.defaultPhone
            discount = value.discount
            totalAmount = value.totalAmount
            estado = value.estado
            motivoRechazo = value.motivoRechazo
            saldoPendiente = value.saldoPendiente
        }
    }

}
