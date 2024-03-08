package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.MisHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single

class RecuperarMisHabilidadesUseCase(
    private val sesionManager: SessionPersonRepository,
    private val misHabilidadesRepository: MisHabilidadesRepository,
    private val campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private val session get() = requireNotNull(sesionManager.get())

    fun recuperar(subscriber: BaseSingleObserver<List<Habilidad>>) {
        val single = recuperarPersona()
            .flatMap { recuperarDatos(it.llaveUA) }
            .flatMap { misHabilidadesRepository.recuperar(it.first, it.second) }

        execute(single, subscriber)
    }

    private fun recuperarPersona(): Single<Persona> {
        return doOnSingle { requireNotNull(session.persona) }
    }

    private fun recuperarDatos(llaveUA: LlaveUA): Single<Pair<LlaveUA, String>> {
        return Single.create {
            val campania = recuperarCampania(llaveUA)
            it.onSuccess(Pair(llaveUA, campania.codigo))
        }
    }

    private fun recuperarCampania(llaveUA: LlaveUA): Campania {
        return requireNotNull(campaniasRepository.obtenerCampaniaActual(llaveUA))
    }
}
