package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.PoolAcuerdos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.PoolCumplimientos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.ProcesadorCumplimientosCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.CumplimientoAcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class ConsolidadoAcuerdosUseCase(
    private val acuerdosRepository: AcuerdosRepository,
    private val campaniasRepository: CampaniasRepository,
    private val cumplimientoAcuerdosRepository: CumplimientoAcuerdosRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val cantidadCampanias = 6

    fun obtener(request: ObtenerRequest) {
        val single = Single.create<Response> { emitter ->
            val persona = recuperarPersona(request)
            val procesador = crearProcesadorCumplimientos(persona.llaveUA)
            val cumplimientosCampania = procesador.procesar().sortedBy { it.campania.codigo }
            val response = Response(cumplimientosCampania)
            emitter.onSuccess(response)
        }
        execute(single, request.subscriber)
    }

    private fun crearProcesadorCumplimientos(llaveUA: LlaveUA): ProcesadorCumplimientosCampania {
        val campanias = recuperarU6C(llaveUA)
        val poolAcuerdos = crearPoolAcuerdos(llaveUA)
        val poolCumplimientos = crearPoolCumplimientosPrecalculados(llaveUA)
        return ProcesadorCumplimientosCampania(campanias, poolCumplimientos, poolAcuerdos)
    }

    private fun recuperarPersona(request: ObtenerRequest) =
        requireNotNull(
            personaRepository.recuperarPersonaPorId(
                request.personaId,
                request.rol
            )
        )

    private fun recuperarU6C(llaveUA: LlaveUA): List<Campania> {
        return campaniasRepository.obtenerPenultimasCampanias(llaveUA).take(cantidadCampanias)
    }

    private fun crearPoolAcuerdos(llaveUA: LlaveUA): PoolAcuerdos {
        val acuerdos = acuerdosRepository.obtener(llaveUA)
        return PoolAcuerdos(acuerdos)
    }

    private fun crearPoolCumplimientosPrecalculados(llaveUA: LlaveUA): PoolCumplimientos {
        val cumplimientos = cumplimientoAcuerdosRepository.obtener(llaveUA)
        return PoolCumplimientos(cumplimientos)
    }

    data class ObtenerRequest(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<Response>
    )

    data class Response(val cumplimientos: List<CumplimientoCampania>)
}
