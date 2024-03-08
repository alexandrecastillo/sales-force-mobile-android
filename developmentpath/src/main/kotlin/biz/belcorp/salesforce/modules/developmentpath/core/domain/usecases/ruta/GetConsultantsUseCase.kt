package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantSectionDevelopmentPathInfo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.ConsultoraRDDRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class GetConsultantsUseCase(
    private val consultoraRDDRepository: ConsultoraRDDRepository,
    private val planRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {


    fun getConsultants(
        planId: Long,
        subscriber: BaseSingleObserver<ConsultantSectionDevelopmentPathInfo>
    ) {
        val single = Single.create<ConsultantSectionDevelopmentPathInfo> {
            val infoPlan = requireNotNull(planRepository.obtenerInfoPlanRdd(planId))
            val consultants = consultoraRDDRepository.getConsultants(planId)

            it.onSuccess(ConsultantSectionDevelopmentPathInfo(consultants, infoPlan.visitedDays))
        }
        execute(single, subscriber)
    }
}
