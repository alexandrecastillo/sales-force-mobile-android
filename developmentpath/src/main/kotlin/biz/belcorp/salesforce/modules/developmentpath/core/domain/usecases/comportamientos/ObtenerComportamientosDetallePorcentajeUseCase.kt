package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientoDetallePorcentajeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants

class ObtenerComportamientosDetallePorcentajeUseCase(
    private val planRepository: RddPlanRepository,
    private val porcentajerepository: ComportamientoDetallePorcentajeRepository,
    private val campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(planId: Long, subscriber: BaseSingleObserver<List<ComportamientoPorcentaje>>) {
        val single = doOnSingle {
            val llaveUA = recuperarUa(planId)
            val campania = recuperarPenultimaCampania(llaveUA).codigo
            porcentajerepository.obtenerPorcentajes(llaveUA, campania)
        }
        execute(single, subscriber)
    }

    private fun recuperarUa(planId: Long): LlaveUA {
        val plan = requireNotNull(planRepository.obtenerInfoPlanRdd(planId))
        return LlaveUA(
            codigoRegion = plan.codigoRegion,
            codigoZona = plan.codigoZona,
            codigoSeccion = plan.codigoSeccion,
            consultoraId = null
        )
    }

    private fun recuperarPenultimaCampania(llaveUA: LlaveUA): Campania {
        return campaniasRepository.obtenerPenultimasCampanias(llaveUA, Constants.UNO).first()
    }

}
