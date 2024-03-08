package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ProgresoReconocimientos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class ProgresoReconocimientoUseCase(
    private val reconocimientosRepository: ReconocimientosEnCampaniaRepository,
    private val comportamientosRepository: ComportamientosRepository,
    private val unidadAdministrativaRepository: UnidadAdministrativaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(request: Request) {
        val single = Single.create<Response> { emitter ->
            val llaveUA = recuperarLlaveUa(request)
            val comportamientos = recuperarComportamientosPorRol(request)
            val reconocimientos = recuperarReconocimientosPorCampania(llaveUA)
            val reconocimientosHistoricos = ProgresoReconocimientos(reconocimientos, comportamientos)
            val progresos = reconocimientosHistoricos.progresoPorComportamiento
            val campanias = reconocimientos.map { it.campania }
            val response = Response(progresos, campanias)
            emitter.onSuccess(response)
        }
        execute(single, request.subscriber)
    }

    private fun recuperarComportamientosPorRol(request: Request) =
        comportamientosRepository.obtenerPorRol(request.rol)

    private fun recuperarLlaveUa(request: Request) =
        requireNotNull(unidadAdministrativaRepository
            .obtenerLlaveUaPorIdPersona(request.personaId, request.rol))

    private fun recuperarReconocimientosPorCampania(llaveUA: LlaveUA): List<TodosLosReconocimientosEnCampania> {
        return reconocimientosRepository.obtenerParaPenultimasCampanias(llaveUA, LIMITE_CAMPANIAS)
    }

    data class Request(val personaId: Long,
                       val rol: Rol,
                       val subscriber: SingleObserver<Response>
    )

    data class Response(val progresosPorComportamiento: List<ProgresoReconocimientos.ProgresoPorComportamiento>,
                        val campanias: List<Campania>)

    companion object {
        private const val LIMITE_CAMPANIAS = 6
    }
}
