package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.TipsOfertaRepository
import io.reactivex.Single

class TipsOfertaUseCase(
    private val personaRepository: RddPersonaRepository,
    private val repository: TipsOfertaRepository,
    private val session: SessionPersonRepository,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerTipsOfertas(request: Request) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.personaRol) }
            .flatMap {
                configurarParams(request, it)
                obtenerTips(request)
            }
        execute(single.toObservable(), request.subscriber)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    private fun configurarParams(request: Request, persona: Persona) {
        request.ua = persona.llaveUA
        request.documento = persona.documento
        request.pais = session.get()?.pais?.codigoIso!!
        request.campania = persona.unidadAdministrativa.campania.codigo
    }

    private fun obtenerTips(request: Request): Single<List<TipOferta<List<Oferta>>>> {
        return repository.obtenerTipsOfertas(request)
    }

    class Request(
        personaId: Long, personaRol: Rol, offline: Boolean,
        val subscriber: BaseObserver<List<TipOferta<List<Oferta>>>>
    ) : Params(personaId, personaRol, offline)
}
