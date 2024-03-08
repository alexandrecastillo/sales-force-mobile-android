package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPropias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.DvRDDIndicatorRepository

class ObtenerMisVisitasPropiasUseCase(private val dvRDDIndicatorRepository: DvRDDIndicatorRepository,
                                      threadExecutor: ThreadExecutor,
                                      postExecutionThread: PostExecutionThread
)
    : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(subscriber: BaseSingleObserver<VisitasPropias>) {
        val single = dvRDDIndicatorRepository.obtenerIndicadorVisitasPropias()
        execute(single, subscriber)
    }
}
