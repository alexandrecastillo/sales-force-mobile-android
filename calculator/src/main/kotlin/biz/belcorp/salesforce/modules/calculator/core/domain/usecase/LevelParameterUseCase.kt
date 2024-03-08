package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.LevelParameter
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.levelparameter.LevelParameterRepository

class LevelParameterUseCase (
    private val repository: LevelParameterRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<List<LevelParameter>>) {
        val list = repository.list()
        execute(list, observer)
    }

    suspend fun parametroPorNivel(nivelId: Int): LevelParameter? {
        return repository.parametroPorNivel(nivelId)
    }
}
