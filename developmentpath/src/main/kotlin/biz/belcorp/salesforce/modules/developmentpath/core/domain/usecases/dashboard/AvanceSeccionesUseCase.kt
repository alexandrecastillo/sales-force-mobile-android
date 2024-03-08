package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddSeccionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.Observable

class AvanceSeccionesUseCase(
    private val rddSeccionRepository: RddSeccionRepository,
    private val unidadAdministrativaRepository: UnidadAdministrativaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(planId: Long, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->

            val zona = requireNotNull(unidadAdministrativaRepository.obtenerUaPorPlan(planId))

            val secciones = rddSeccionRepository.recuperarParaAvance(zona.codigo)

            val response = Response(secciones)
            emitter.onNext(response)
            emitter.onComplete()
        }

        execute(observable, subscriber)
    }

    class Response(val secciones: List<SeccionRdd>)
}
