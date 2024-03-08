package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.ingresosextra

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.IngresosExtraRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doAsync
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver

class IngresosExtraUseCase(
    private val personaRepository: RddPersonaRepository,
    private val marcaRepository: IngresosExtraRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerDatosFront(
        personaId: Long,
        personaRol: Rol,
        subscriber: BaseSingleObserver<List<OtraMarca>>
    ) {
        val single = doOnSingle { obtenerPersona(personaId, personaRol) }
            .flatMap { marcaRepository.obtenerMarcas(it.llaveUA, personaId) }
            .map { it -> it.filter { it.showFront } }
        execute(single, subscriber)
    }

    fun obtenerDatosMore(
        personaId: Long,
        personaRol: Rol,
        subscriber: BaseSingleObserver<List<OtraMarca>>
    ) {
        val single = doOnSingle { obtenerPersona(personaId, personaRol) }
            .flatMap { marcaRepository.obtenerMarcas(it.llaveUA, personaId) }
            .map { it -> it.filter { !it.showFront } }
        execute(single, subscriber)
    }

    fun obtenerDatosMoreChecked(
        personaId: Long,
        personaRol: Rol,
        subscriber: BaseSingleObserver<List<OtraMarca>>
    ) {

        val single = doOnSingle { obtenerPersona(personaId, personaRol) }
            .flatMap { marcaRepository.obtenerMarcas(it.llaveUA, personaId) }
            .map { it -> it.filter { !it.showFront && it.checked } }
        execute(single, subscriber)
    }

    fun actualizarCheck(model: OtraMarca, subscriber: CompletableObserver) {
        val observable = marcaRepository.actualizarMarca(model)
            .doOnComplete { marcaRepository.sincronizar().doAsync() }
        execute(observable, subscriber)
    }

    fun actualizarCheckList(data: List<OtraMarca>, subscriber: BaseSingleObserver<Boolean>) {
        val observable = marcaRepository.actualizarMarcaList(data)
            .andThen(marcaRepository.sincronizar())
            .andThen(Single.just(true))
            .onErrorResumeNext {
                Single.just(false)
            }
        execute(observable, subscriber)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))
}
