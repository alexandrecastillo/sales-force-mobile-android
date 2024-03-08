package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.DocumentoRepository

class DocumentoUseCase(
    private val repository: DocumentoRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerDocumentos(request: ParamsDocumentos) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.campaniaActual = it.unidadAdministrativa.campania.codigo
                request.filtrarPorNombreOpciones = request.opciones ?: emptyList()
                repository.obtenerDocumentos(request)
            }
            .map { it.take(LIMITE_DOCUMENTOS) }
        execute(single, request.observer)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    class ParamsDocumentos(
        personaId: Long, personaRol: Rol, opciones: List<String>? = emptyList(),
        val observer: BaseSingleObserver<List<Documento>>
    ) : Params(personaId, personaRol, opciones, "")

    companion object {
        private const val LIMITE_DOCUMENTOS = 2
    }
}
