package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PaymentDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.paymentdetail.PaymentDetailRepository

class PaymentDetailUseCase (
    private val repository: PaymentDetailRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<PaymentDetail>>) {
        val list = repository.list()
        execute(list, observer)
    }
}
