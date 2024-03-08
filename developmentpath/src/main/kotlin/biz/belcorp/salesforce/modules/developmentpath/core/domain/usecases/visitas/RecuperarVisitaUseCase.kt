package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

import io.reactivex.Single
import io.reactivex.SingleObserver

class RecuperarVisitaUseCase(
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(request: Request) {
        val single = Single.create<Visita> {
            val visita =
                    requireNotNull(personaRepository.recuperarVisita(request.visitaId)) {
                        "No se encontró visita"
                    }
            it.onSuccess(visita)
        }

        execute(single, request.subscriber)
    }

    class Request(val visitaId: Long,
                  val subscriber: SingleObserver<Visita>)
}
