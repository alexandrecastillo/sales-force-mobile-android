package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips.TipsVisitaRepository
import io.reactivex.Observable

class ObtenerTipsVisitaUseCase(
    private val tipsVisitaRepository: TipsVisitaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(request: Request) {
        val observable = Observable.create<Response> { emitter ->
            val grupoTipsVisita = checkNotNull(tipsVisitaRepository
                .obtenerTipsDePersona(request.personaId, request.rol)) {
                "No se encontraron tips para la persona con id ${request.personaId}"
            }
            emitter.onNext(Response(grupoTipsVisita))
        }
        execute(observable, request.subscriber)
    }

    class Request(val personaId: Long, val rol: Rol, val subscriber: BaseObserver<Response>)

    class RequestTip(val personaId: Long, val rol: Rol, val subscriber: BaseObserver<Long>)

    class Response(val grupoTipsVisita: GrupoTipsVisita)

    fun recuperarTipId(request: RequestTip) {
        val observable = Observable.create<Long> { emitter ->
            val tipId = tipsVisitaRepository.obtenerIdCabeceraTip(request.personaId, request.rol)
                ?: -1L
            emitter.onNext(tipId)
        }
        execute(observable, request.subscriber)
    }
}
