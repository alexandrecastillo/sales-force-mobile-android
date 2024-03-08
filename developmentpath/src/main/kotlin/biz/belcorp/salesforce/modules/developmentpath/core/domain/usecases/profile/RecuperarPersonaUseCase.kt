package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single

class RecuperarPersonaUseCase(
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(request: Request) {
        val single = Single.create<PersonaRdd> {
            val persona = personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
                ?: error(Constant.PERSON_NOT_FOUND)
            it.onSuccess(persona)
        }
        execute(single, request.subscriber)
    }

    class Request(
        val personaId: Long,
        val rol: Rol,
        val subscriber: BaseSingleObserver<PersonaRdd>
    )
}
