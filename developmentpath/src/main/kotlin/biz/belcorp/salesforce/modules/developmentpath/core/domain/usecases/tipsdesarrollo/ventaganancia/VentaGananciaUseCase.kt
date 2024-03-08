package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.ventaganancia

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia.VentaGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.VentaGananciaRepository

class VentaGananciaUseCase(
    private val personaRepository: RddPersonaRepository,
    private val repository: VentaGananciaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerVentaGanancia(request: Request) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.ua = it.llaveUA
                repository.obtenerVentaGanancia(request)
            }
        execute(single, request.subscriber)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    class Request(personaId: Long, personaRol: Rol, opcion: String,
                  val subscriber: BaseSingleObserver<VentaGanancia>
    ) : Params(personaId, personaRol, null, opcion)
}
