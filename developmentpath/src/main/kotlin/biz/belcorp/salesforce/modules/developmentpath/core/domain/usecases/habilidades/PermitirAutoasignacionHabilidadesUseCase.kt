package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single

class PermitirAutoasignacionHabilidadesUseCase(
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun permitir(subscriber: BaseSingleObserver<Boolean>) {
        val single = Single.create<Boolean> {
            val rol = requireNotNull(sesionManager.get()?.rol)
            val permitido = rol.permiteAutosignacion()

            it.onSuccess(permitido)
        }
        execute(single, subscriber)
    }

    private fun Rol.permiteAutosignacion(): Boolean {
        return when (this) {
            Rol.GERENTE_REGION -> true
            else -> false
        }
    }
}
