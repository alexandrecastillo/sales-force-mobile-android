package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class AcuerdosCampaniaActualUseCase(
    private val acuerdosRepository: AcuerdosRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(request: Request) {
        val single = Single.create<Response> { emitter ->
            val persona = recuperarPersona(request)
            val acuerdos = acuerdosRepository.obtenerParaCampaniaActual(persona.llaveUA)
            val response = Response(acuerdos = acuerdos)

            emitter.onSuccess(response)
        }
        return execute(single, request.subscriber)
    }

    private fun recuperarPersona(request: Request) =
        requireNotNull(personaRepository.recuperarPersonaPorId(request.personaId, request.rol))

    data class Request(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<Response>
    )

    data class Response(val acuerdos: List<Acuerdo>)
}
