package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.SingleObserver

class ComportamientosCampaniaActualUseCase(
    private val reconocimientosRepository: ReconocimientosEnCampaniaRepository,
    private val unidadAdministrativaRepository: UnidadAdministrativaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(request: Request) {
        val single = doOnSingle {
            val llaveUa = recuperarLlaveUaDePersona(request)
            reconocimientosRepository
                .obtenerParaCampaniaActual(llaveUa)
        }
        return execute(single, request.subscriber)
    }

    private fun recuperarLlaveUaDePersona(request: Request): LlaveUA {
        val llave = unidadAdministrativaRepository
            .obtenerLlaveUaPorIdPersona(request.personaId, request.rol)
        return requireNotNull(llave) { ERROR_UA }
    }

    data class Request(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<TodosLosReconocimientosEnCampania>
    )

    companion object {
        private const val ERROR_UA = "Error al recuperar datos de unidad administrativa"
    }
}
