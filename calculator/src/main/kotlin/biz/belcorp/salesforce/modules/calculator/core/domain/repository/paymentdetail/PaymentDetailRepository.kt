package biz.belcorp.salesforce.modules.calculator.core.domain.repository.paymentdetail

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PaymentDetail
import io.reactivex.Single

interface PaymentDetailRepository {
    fun list(): Single<List<PaymentDetail>>
}
