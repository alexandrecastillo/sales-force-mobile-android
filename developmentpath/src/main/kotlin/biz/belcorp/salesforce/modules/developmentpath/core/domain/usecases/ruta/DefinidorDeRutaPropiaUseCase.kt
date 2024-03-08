package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.diferenciaDeNivel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class DefinidorDeRutaPropiaUseCase(
    private val sesionRepository: SessionPersonRepository,
    private val planRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(planId: Long, observer: BaseSingleObserver<Boolean>) {
        val single = Single.create<Boolean> {
            val plan = requireNotNull(planRepository.obtenerInfoPlanRdd(planId))
            val sesion = requireNotNull(sesionRepository.get())
            it.onSuccess(sesion.rol diferenciaDeNivel plan.rol == 0)
        }
        execute(single, observer)
    }
}
