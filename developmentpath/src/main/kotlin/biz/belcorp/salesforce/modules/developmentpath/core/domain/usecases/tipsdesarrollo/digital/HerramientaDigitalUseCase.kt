package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.HerramientaDigitalRepository
import io.reactivex.Single

class HerramientaDigitalUseCase(
    private val personaRepository: RddPersonaRepository,
    private val repository: HerramientaDigitalRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerHerramientasDigitales(request: DigitalRequest) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.ua = it.llaveUA
                repository.obtenerDigital(request)
            }
            .flatMap { list ->
                list.forEach {
                    it.tips = emptyList()
                    it.videos = emptyList()
                }
                Single.just(list)
            }
        execute(single, request.subscriber)
    }

    fun obtenerTipsVideos(request: TipsRequest) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                request.ua = it.llaveUA
                repository.obtenerDigital(request)
            }
            .flatMap { list ->
                if (!list.isNullOrEmpty()) {
                    val pair = HerramientaDigitalRule(list)
                        .obtenerListaDeTipsYVideosProcesados()
                    Single.just(pair)
                } else {
                    Single.just(Pair(emptyList(), emptyList()))
                }
            }
        execute(single, request.subscriber)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    class DigitalRequest(
        personaId: Long, personaRol: Rol, opcion: String,
        val subscriber: BaseSingleObserver<List<HerramientaDigital>>
    ) : Params(personaId, personaRol, null, opcion)

    class TipsRequest(
        personaId: Long, personaRol: Rol, opcion: String,
        val subscriber: BaseSingleObserver<Pair<List<Tip>, List<Video>>>
    ) : Params(personaId, personaRol, null, opcion)
}
