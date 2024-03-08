package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.FocosHabilidadesEnDashboardRepository
import io.reactivex.Observable

class FocosZonaUseCase(
    private val focosHabilidadesEnDashboardRepository: FocosHabilidadesEnDashboardRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->
            val secciones = focosHabilidadesEnDashboardRepository.obtenerSecciones()

            emitter.onNext(Response(secciones))
            emitter.onComplete()
        }

        execute(observable, subscriber)
    }

    class Response(val secciones: List<SeccionRdd>)
}
