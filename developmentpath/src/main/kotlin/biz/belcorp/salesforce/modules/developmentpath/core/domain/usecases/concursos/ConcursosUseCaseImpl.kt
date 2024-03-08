package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.ConcursoDetalle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos.ConcursosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single

class ConcursosUseCaseImpl(
    private val concursosRepository: ConcursosRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread), ConcursosUseCase {

    override fun obtenerConcursos(request: Request) {
        val single = obtenerPersona(request)
            .flatMap { concursosRepository.buscarConcurso(it) }
            .map { Response(concursos = it.data) }
        execute(single, request.subscriber)
    }


    override fun obtenerConcursosPorTipo(request: Request) {
        val single = obtenerPersona(request)
            .flatMap { concursosRepository.buscarConcurso(it) }
            .map {
                val detalles = it.data.filter { it.tipo == request.tipo }
                Response(concursos = detalles)
            }
        execute(single, request.subscriber)
    }

    private fun obtenerPersona(request: Request): Single<PersonaRdd> {
        val identificador = PersonaRdd.Identificador(request.personaId, request.rol)
        return personaRepository.singleRecuperarPersonaPorId(identificador)
    }

    class Request(
        val personaId: Long,
        val rol: Rol,
        val tipo: String = "",
        val subscriber: BaseSingleObserver<Response>
    )

    class Response(val concursos: List<ConcursoDetalle> = emptyList())
}
