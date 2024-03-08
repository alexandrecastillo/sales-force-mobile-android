package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddZonaRepository
import io.reactivex.Single

class AvanceZonasUseCase(
    private val repository: RddZonaRepository,
    private val planRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(request: Request) {
        val single = Single.create<Response> { emitter ->
            val infoPlan = requireNotNull(planRepository.obtenerInfoPlanRdd(request.planId))
            val zonas = repository.recuperarParaAvance(requireNotNull(infoPlan.codigoRegion))

            emitter.onSuccess(Response(zonas))
        }

        execute(single, request.subscriber)
    }

    data class Request(
        val planId: Long,
        val subscriber: BaseSingleObserver<Response>
    )

    data class Response(val zonas: List<ZonaRdd>)
}
