package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.UneteConfiguracionRepository

class UneteConfiguracionUseCase(
    private val repository: UneteConfiguracionRepository,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun get(useCaseSubscriber: BaseSingleObserver<Configuracion>, pais: String, rol: String) {
        val observable = repository.get(pais, rol)
        execute(observable, useCaseSubscriber)
    }

}
