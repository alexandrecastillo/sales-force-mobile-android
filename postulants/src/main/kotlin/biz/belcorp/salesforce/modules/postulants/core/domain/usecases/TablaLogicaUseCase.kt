package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.TablaLogicaRepository

class TablaLogicaUseCase(
    private val repository: TablaLogicaRepository,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun list(useCaseSubscriber: BaseSingleObserver<List<TablaLogica>>, tipoParametro: Int) {
        val observable = repository.list(tipoParametro)
        execute(observable, useCaseSubscriber)
    }

}
