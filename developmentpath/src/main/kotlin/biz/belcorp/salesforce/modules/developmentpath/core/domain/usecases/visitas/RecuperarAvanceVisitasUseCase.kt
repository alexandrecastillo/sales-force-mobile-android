package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AvanceVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class RecuperarAvanceVisitasUseCase(
    private val planRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(planId: Long, subscriber: BaseSingleObserver<AvanceVisitas>) {
        val single = Single.create<AvanceVisitas> {
            val infoPlan = requireNotNull(planRepository.obtenerInfoPlanRdd(planId))
            val avance = AvanceVisitas(
                visitadas = infoPlan.visitadas,
                planificadas = infoPlan.planificadas,
                tipoHijas = infoPlan.rol.childAsText()
            )
            it.onSuccess(avance)
        }
        execute(single, subscriber)
    }
}
