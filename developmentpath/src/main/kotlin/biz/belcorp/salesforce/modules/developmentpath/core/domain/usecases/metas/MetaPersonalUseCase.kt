package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaPersonalRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.CompletableObserver
import io.reactivex.Single

class MetaPersonalUseCase(
    private val metasRepository: MetaPersonalRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun grabar(meta: MetaPersonal, rol: Rol, subscriber: BaseSingleObserver<MetaPersonal>) {

        val single = meta.completarCampos(rol)
            .flatMap { metasRepository.guardar(meta) }
            .doAfterSuccess { sincronizar(SincronizacionObserver()) }

        execute(single, subscriber)
    }

    fun actualizar(meta: MetaPersonal, rol: Rol, subscriber: BaseSingleObserver<MetaPersonal>) {

        val single = meta.completarCampos(rol)
            .flatMap { metasRepository.actualizar(meta) }
            .doAfterSuccess { sincronizar(SincronizacionObserver()) }

        execute(single, subscriber)
    }

    fun eliminar(meta: MetaPersonal, subscriber: CompletableObserver) {

        val completable = metasRepository.eliminar(meta)
            .doAfterTerminate { sincronizar(SincronizacionObserver()) }

        execute(completable, subscriber)
    }

    fun listar(personaId: Long, subscriber: BaseSingleObserver<List<MetaPersonal>>) {

        val single = metasRepository.listar(personaId)

        execute(single, subscriber)
    }

    fun sincronizar(subscriber: CompletableObserver) {

        val completable = metasRepository.sincronizar()

        execute(completable, subscriber)
    }

    private fun MetaPersonal.completarCampos(rol: Rol): Single<MetaPersonal> {
        val identificador = PersonaRdd.Identificador(personaId, rol)
        return personaRepository.singleRecuperarPersonaPorId(identificador)
            .map {
                campania = it.unidadAdministrativa.campania.codigo
                asignarUa(it.llaveUA)
                this
            }
    }

    private inner class SincronizacionObserver : BaseCompletableObserver() {

        override fun onComplete() {
            print("Sincronización exitosa")
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }
}
