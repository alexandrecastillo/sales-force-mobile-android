package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddRegionRepository

class AvanceRegionesUseCase(private val regionesRepository: RddRegionRepository,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(observer: BaseSingleObserver<Response>) {
        val single = regionesRepository.recuperarParaAvance().map { Response(it) }
        execute(single, observer)
    }

    class Response(val regiones: List<RegionRdd>)
}
