package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class RecuperarTituloDesarrolloUaUseCase(
    private val planRepository: RddPlanRepository,
    private val campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(planId: Long, subscriber: BaseSingleObserver<Response>) {
        val single = Single.create<Response> {
            val infoPlan = recuperarInfoPlan(planId)
            val unidadAdministrativa = infoPlan.rol.uaBajoCargo()
            val campaniaActual = recuperarCampaniaActual(infoPlan.llaveUA)
            val campaniaAnterior = recuperarCampaniaAnterior(infoPlan.llaveUA)
            val response = Response(
                unidadAdministrativa = unidadAdministrativa,
                campaniaActual = campaniaActual,
                campaniaAnterior = campaniaAnterior,
                rolPlan = infoPlan.rol,
                infoPlan = infoPlan
            )

            it.onSuccess(response)
        }
        execute(single, subscriber)
    }

    private fun recuperarInfoPlan(planId: Long): InfoPlanRdd {
        val info = planRepository.obtenerInfoPlanRdd(planId)

        return requireNotNull(info) { "Error al recuperar información de plan" }
    }

    private fun recuperarCampaniaActual(ua: LlaveUA): Campania {
        val campania = campaniasRepository.obtenerCampaniaActual(ua)

        return requireNotNull(campania) { "Error al recuperar campaña" }
    }

    private fun recuperarCampaniaAnterior(ua: LlaveUA): Campania {
        val campania = campaniasRepository.obtenerCampaniaInmediataAnterior(ua)

        return requireNotNull(campania) { "Error al recuperar campaña" }
    }

    class Response(
        val unidadAdministrativa: String,
        val campaniaActual: Campania,
        val campaniaAnterior: Campania,
        val rolPlan: Rol,
        val infoPlan: InfoPlanRdd
    )
}
