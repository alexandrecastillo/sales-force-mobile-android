package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.CaminoBrillanteTipsRepository
import io.reactivex.Single

class CaminoBrillanteTipsUseCase(
    private val personaRepository: RddPersonaRepository,
    private val repository: CaminoBrillanteTipsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerTips(request: Request) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.ua = it.llaveUA
                repository.obtenerTips(request)
            }
            .flatMap {
                Single.just(Pair(it.tips, it.videos))
            }
        execute(single, request.subscriber)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    class Request(
        personaId: Long, personaRol: Rol, opcion: String,
        val subscriber: BaseSingleObserver<Pair<List<Tip>, List<Video>>>
    ) : Params(personaId, personaRol, null, opcion)
}
