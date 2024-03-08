package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.fixedamount.CalculadoraMontoFijoRepository
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo

class GetCalculadoraMontoFijoUseCase
constructor(
    private val repository: CalculadoraMontoFijoRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<CalculadoraMontoFijo>>) {
        val list = repository.all()
        execute(list, observer)
    }
}
