package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.TipoMeta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.TipoMetaRepository

class TipoMetaUseCase(
    private val repository: TipoMetaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(observer: BaseSingleObserver<List<TipoMeta>>) {
        val observable = this.repository.obtener()
        execute(observable, observer)
    }
}
