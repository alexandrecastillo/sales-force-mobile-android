package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelActualCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.ProgresoCaminoBrillanteRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallelWithResult
import io.reactivex.Single

class ProgresoCaminoBrillanteUseCase(
    private val personaRepository: RddPersonaRepository,
    private val caminoBrillanteRepository: ProgresoCaminoBrillanteRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerProgreso(params: Params) {
        val persona = obtenerPersona(params.personaId, params.rol)
        val single = persona.doInParallelWithResult(
            { obtnerNiveles() },
            { obtenerNivelActual(it.llaveUA) },
            { niveles: List<NivelCaminoBrillante>, nivelActual: NivelActualCaminoBrillante ->
                Single.just(crearRespuesta(niveles, nivelActual))
            }
        )
        execute(single, params.subscriber)
    }

    private fun obtenerPersona(personaId: Long, rol: Rol): Single<PersonaRdd> {
        return personaRepository.singleRecuperarPersonaPorId(
            PersonaRdd.Identificador(
                personaId,
                rol
            )
        )
    }

    private fun obtnerNiveles(): Single<List<NivelCaminoBrillante>> {
        return caminoBrillanteRepository.obtenerNiveles()
    }

    private fun obtenerNivelActual(llaveUA: LlaveUA): Single<NivelActualCaminoBrillante> {
        return caminoBrillanteRepository.obtenerNivelActual(llaveUA)
    }

    private fun actualizarNivelActual(
        niveles: List<NivelCaminoBrillante>,
        nivelActual: NivelActualCaminoBrillante
    ) {
        niveles.firstOrNull { it.id == nivelActual.nivelActual }?.esNivelActual = true
    }

    private fun crearRespuesta(
        niveles: List<NivelCaminoBrillante>,
        nivelActual: NivelActualCaminoBrillante
    ): ProgresoCaminoBrillante {
        actualizarNivelActual(niveles, nivelActual)
        return ProgresoCaminoBrillante(niveles = niveles, progreso = nivelActual)
    }

    class Params(
        val personaId: Long, val rol: Rol,
        val subscriber: BaseSingleObserver<ProgresoCaminoBrillante>
    )
}
