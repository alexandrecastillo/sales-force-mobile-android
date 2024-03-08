package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.secciones.SeccionRepository

class SeccionesUseCase(
    private val repository: SeccionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun getAll(observer: BaseObserver<List<Seccion>>) {
        val observable = this.repository.all
        execute(observable, observer)
    }

}
