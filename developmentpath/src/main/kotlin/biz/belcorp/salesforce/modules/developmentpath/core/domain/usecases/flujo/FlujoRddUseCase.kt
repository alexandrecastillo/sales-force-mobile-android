package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dashboard.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Observable

class FlujoRddUseCase(
    private val sesionManager: SessionPersonRepository,
    private val rddPlanRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(suscriber: BaseObserver<Flujo>) {
        val observable = Observable.create<Flujo> { emitter ->

            val sesion = sesionManager.get()
            val idPlan = rddPlanRepository.obtenerPlanParaRolDuenio(sesion.rol) ?: -1L

            val flujo = obtenerFlujo(sesion, idPlan)

            emitter.onNext(flujo)
            emitter.onComplete()
        }

        execute(observable, suscriber)
    }

    private fun obtenerFlujo(sesion: Sesion, idPlan: Long): Flujo {
        val rol = sesion.rol

        return when (rol) {
            Rol.DIRECTOR_VENTAS -> FlujoDV(idPlan)
            Rol.GERENTE_REGION -> FlujoGR(idPlan)
            Rol.GERENTE_ZONA -> FlujoGZ(idPlan)
            Rol.SOCIA_EMPRESARIA -> FlujoSE(idPlan)
            else -> FlujoNoDefinido(idPlan)
        }
    }
}
