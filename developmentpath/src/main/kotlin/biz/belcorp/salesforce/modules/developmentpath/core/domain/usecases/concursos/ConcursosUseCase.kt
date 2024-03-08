package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos

interface ConcursosUseCase {
    fun obtenerConcursos(request: ConcursosUseCaseImpl.Request)

    fun obtenerConcursosPorTipo(request: ConcursosUseCaseImpl.Request)
}
