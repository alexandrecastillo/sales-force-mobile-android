package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.CobranzaYGananciaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class CobranzaYGananciaUseCase(
    private val personaDataRepository: RddPersonaRepository,
    private val cobranzaYGananciaRepository: CobranzaYGananciaRepository,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerCobranzaGanancia(request: RequestCobranzaGanancia) {
        val flowable = personaDataRepository
            .singleRecuperarPersonaPorId(request.identificador)
            .flatMapPublisher { persona ->
                cobranzaYGananciaRepository.obtenerCobranzaGanancia(persona.llaveUA)
            }
        execute(flowable.toObservable(), request.subscriber)
    }

    class RequestCobranzaGanancia(
        val identificador: PersonaRdd.Identificador,
        val subscriber: BaseObserver<CobranzaYGanancia>
    )
}
