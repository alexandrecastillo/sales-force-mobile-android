package biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.mapper

import biz.belcorp.salesforce.core.entities.calculator.PaymentDetailEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PaymentDetail

class PaymentDetailEntityDataMapper {

    fun parsePaymentDetail(list: List<PaymentDetailEntity>?): List<PaymentDetail>? {
        val entity = arrayListOf<PaymentDetail>()

        list?.forEach {
            entity.add(PaymentDetail(
                it.constancia,
                it.cantidad,
                it.mensaje))
        }

        return entity
    }
}
