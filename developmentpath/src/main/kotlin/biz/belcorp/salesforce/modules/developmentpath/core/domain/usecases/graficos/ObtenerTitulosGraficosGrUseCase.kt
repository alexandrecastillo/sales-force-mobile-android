package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class ObtenerTitulosGraficosGrUseCase(
    private val graficosGrRepository: GraficosGrRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(personaId: Long, rol: Rol, subscriber: BaseSingleObserver<List<GraficoGr>>) {
        val single = personaRepository
            .singleRecuperarPersonaPorId(PersonaRdd.Identificador(personaId, rol))
            .flatMap { persona ->
                graficosGrRepository.obtenerTitulosGraficos(persona.llaveUA)
            }
        execute(single, subscriber)
    }
}
