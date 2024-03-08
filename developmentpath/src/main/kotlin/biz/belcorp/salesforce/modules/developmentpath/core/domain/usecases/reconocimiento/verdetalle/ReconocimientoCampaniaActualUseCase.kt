package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoComportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository

class ReconocimientoCampaniaActualUseCase(
    private val reconocimientosRepository: ReconocimientosEnCampaniaRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun recuperarDataInicial(request: ReconocerInput.Recuperar) {
        val single = doOnSingle {
            val persona = recuperarPersona(request)
            val reconocimientos = recuperarReconocimientos(persona.llaveUA)
            ReconocerOutput.Recuperar(persona, reconocimientos)
        }
        return execute(single, request.subscriber)
    }

    private fun recuperarPersona(request: ReconocerInput.Recuperar) =
        requireNotNull(personaRepository.recuperarPersonaPorId(request.personaId, request.rol))

    private fun recuperarReconocimientos(llaveUA: LlaveUA): List<ReconocimientoComportamiento> {
        return reconocimientosRepository.obtenerParaCampaniaActual(llaveUA).reconocimiento
    }
}
