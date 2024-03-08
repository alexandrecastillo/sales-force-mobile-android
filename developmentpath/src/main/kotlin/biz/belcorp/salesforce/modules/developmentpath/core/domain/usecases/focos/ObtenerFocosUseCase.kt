package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.ListadoFocosEnAsignacionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Observable

class ObtenerFocosUseCase(
    private val listadoFocosEnAsignacionRepository: ListadoFocosEnAsignacionRepository,
    val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :

    UseCase(threadExecutor, postExecutionThread) {

    fun recuperarFocosParaSocia(subscriber: BaseObserver<List<Foco>>) {
        val observable = Observable.create<List<Foco>> {

            val sociaId = sesionManager.get().persona.id
            val focos =
                listadoFocosEnAsignacionRepository.recuperarFocosPara(sociaId, Rol.GERENTE_ZONA)

            it.onNext(focos)
        }
        execute(observable, subscriber)
    }
}
