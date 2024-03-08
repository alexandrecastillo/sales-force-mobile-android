package biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail

import biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.data.PaymentDetailDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.mapper.PaymentDetailEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PaymentDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.paymentdetail.PaymentDetailRepository
import io.reactivex.Single

class PaymentDetailDataRepository (
    private val paymentDetailDBDataStore: PaymentDetailDBDataStore,
    private val paymentDetailEntityDataMapper: PaymentDetailEntityDataMapper) : PaymentDetailRepository {

    override fun list(): Single<List<PaymentDetail>> {
        return paymentDetailDBDataStore.all().map {
            paymentDetailEntityDataMapper.parsePaymentDetail(it)
        }
    }
}
