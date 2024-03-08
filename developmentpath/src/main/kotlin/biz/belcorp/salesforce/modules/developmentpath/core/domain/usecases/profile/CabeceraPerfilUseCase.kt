package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Observable

class CabeceraPerfilUseCase(
    private val rddPersonaRepository: RddPersonaRepository,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerDatos(id: Long, rol: Rol, subscriber: BaseObserver<Persona>) {
        val observable = Observable.create<Persona> { emmiter ->
            val persona = checkNotNull(rddPersonaRepository.recuperarPersonaPorId(id, rol))
            val prefijo = sesionManager.get().pais?.prefijo.orEmpty()
            persona.directorio?.configurarPrefijo(prefijo)
            emmiter.onNext(persona)
        }
        execute(observable, subscriber)
    }
}
