package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Observable

class PlanIdUseCase(
    private val rddPlanRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperarPlanIdPorPersona(peticion: Peticion) {
        val observable = Observable.create<RespuestaRol> {
            val identificador = PersonaRdd.Identificador(peticion.personaId, peticion.rol)
            val infoPlanRdd = requireNotNull(
                rddPlanRepository.obtenerPlanAlQuePerteneceUnaPersona(identificador)
            )

            val respuesta = RespuestaRol(infoPlanRdd.planId, infoPlanRdd.rol)

            it.onNext(respuesta)
            it.onComplete()
        }

        execute(observable, peticion.subscriber)
    }

    class RespuestaRol(
        val planId: Long,
        val rol: Rol
    )

    class Peticion(
        val personaId: Long,
        val rol: Rol,
        val subscriber: BaseObserver<RespuestaRol>
    )
}
