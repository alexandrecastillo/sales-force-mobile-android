package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.MisFocosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single

class RecuperarMisFocosUseCase(
    private val sesionManager: SessionPersonRepository,
    private val misFocosRepository: MisFocosRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(subscriber: BaseSingleObserver<List<Foco>>) {

        val single = recuperarPersona()
            .flatMap { persona -> misFocosRepository.recuperar(persona.llaveUA) }

        execute(single, subscriber)
    }

    fun obtenerRol(): String {
        val rol = requireNotNull(sesionManager.get()?.persona)
        return rol.rol.codigoRol
    }

    private fun recuperarPersona(): Single<Persona> {
        return Single.create {
            val persona = requireNotNull(sesionManager.get()?.persona)

            it.onSuccess(persona)
        }
    }
}
