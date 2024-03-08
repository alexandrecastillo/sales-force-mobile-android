package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.navegacion.BarraNavegacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class BarraNavegacionUseCase(private val planRepository: RddPlanRepository,
                             private val sesionManager: SessionPersonRepository,
                             private val unidadAdministrativaRepository: UnidadAdministrativaRepository,
                             threadExecutor: ThreadExecutor,
                             postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtenerBarra(planId: Long, subscriber: SingleObserver<Response>) {

        val single = Single.create<Response> { emitter ->
            val rolSesion = checkNotNull(sesionManager.get().rol)
            val infoPlan = checkNotNull(planRepository.obtenerInfoPlanRdd(planId))
            val ua = checkNotNull(unidadAdministrativaRepository.obtenerUaPorPlan(planId))

            val barra = BarraNavegacion.construir(rolSesion = rolSesion,
                rolDuenioRuta = infoPlan.rol,
                unidadAdministrativa = ua)

            val response = Response(barra = barra)

            emitter.onSuccess(response)
        }

        execute(single, subscriber)
    }

    fun obetenerinfoPlan(planId: Long): InfoPlanRdd? {
        return planRepository.obtenerInfoPlanRdd(planId)
    }

    class Response(val barra: BarraNavegacion)

}
