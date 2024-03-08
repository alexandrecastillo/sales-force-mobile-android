package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DesarrolloHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.DesarrolloHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class GetDesarrolloHabilidadesUseCase(private val desarrolloHabilidadesRepository: DesarrolloHabilidadesRepository,
                                      private val campaniasRepository: CampaniasRepository,
                                      private val planRepository: RddPlanRepository,
                                      threadExecutor: ThreadExecutor,
                                      postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun getDesarrolloHabilidades(planId: Long, subscriber: BaseSingleObserver<Response>) {

        val single = recuperarPlan(planId)
            .flatMap { infoPlan ->
                obtenerHabilidadesYCampanias(infoPlan.llaveUA)
                    .map { response ->
                        response.completarRol(infoPlan.rol)
                    }
            }

        execute(single, subscriber)
    }

    private fun obtenerHabilidadesYCampanias(llaveUA: LlaveUA): Single<Response> {
        return campaniasRepository.obtenerCampanias(llaveUA)
            .doInParallel(desarrolloHabilidadesRepository.getDesarrolloHabilidades(llaveUA))
            .flatMap { crearResponse(it.first, it.second) }
    }


    private fun recuperarPlan(planId: Long): Single<InfoPlanRdd> {
        return doOnSingle {
            requireNotNull(planRepository.obtenerInfoPlanRdd(planId))
        }
    }

    private fun crearResponse(campanias: List<Campania>, habilidades: List<DesarrolloHabilidad>):
        Single<Response> {
        return doOnSingle {
            require(campanias.size > 1)
            val campaniaActual = campanias.first()
            val campaniaAnterior = campanias[1]

            Response(
                habilidades = habilidades,
                campaniaActual = campaniaActual,
                campaniaAnterior = campaniaAnterior)
        }
    }

    class Response(val habilidades: List<DesarrolloHabilidad>,
                   val campaniaActual: Campania,
                   val campaniaAnterior: Campania,
                   var rol: Rol = Rol.NINGUNO) {

        fun completarRol(rol: Rol): Response {
            this.rol = rol
            return this
        }
    }
}
