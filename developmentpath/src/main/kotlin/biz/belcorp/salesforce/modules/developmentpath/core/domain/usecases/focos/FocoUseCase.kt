package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.FocoRepository

class FocoUseCase(
    private val focoRepository: FocoRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtenerFocos(useCaseSubscriber: BaseObserver<List<CabeceraFoco>>, segmentoId: Int) {
        val observable = focoRepository.obtener(segmentoId)
        execute(observable, useCaseSubscriber)
    }

    fun tieneFocos(useCaseSubscriber: BaseObserver<Boolean>, segmentoId: Int) {
        val observable = focoRepository.hasData(segmentoId)
        execute(observable, useCaseSubscriber)
    }
}
