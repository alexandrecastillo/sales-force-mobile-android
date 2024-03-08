package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single


class ObtenerCampaniasRddUseCase(
    private val repository: CampaniasRepository,
    sesionRepository: SessionRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : ObtenerCampaniasUseCase(
    repository, sesionRepository, threadExecutor, postExecutionThread

) {

    fun recuperarCampaniaActual(personaId: Long, rol: Rol): Single<Campania> {
        val identificador = PersonaRdd.Identificador(personaId, rol)
        return personaRepository.singleRecuperarPersonaPorId(identificador)
            .flatMap { recuperarCampaniaActual(it.llaveUA) }
    }

    fun getLatestCampaignsSync(request: Request.GetLatestCampaigns){
        val person = requireNotNull(
            personaRepository.recuperarPersonaPorId(
                request.personaId,
                request.rol
            )
        )

        val single = Single.create<Response.GetLatestCampaigns>{
                emmiter ->
            val latestCampaigns = getCampaignsSync(person.llaveUA).take(3)
            emmiter.onSuccess(Response.GetLatestCampaigns(latestCampaigns))
        }
        execute(single, request.subscriber)
    }

}
