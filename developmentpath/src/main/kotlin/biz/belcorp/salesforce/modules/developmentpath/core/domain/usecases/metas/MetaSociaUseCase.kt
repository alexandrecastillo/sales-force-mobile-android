package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.MetasSociaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class MetaSociaUseCase(
    private val metaRepository: MetasSociaRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun guardar(request: Request) {
        val complete = doOnSingle { obtenerPersona(request.personaId, request.rol) }
            .flatMapCompletable {
                val metaSocia = request.modelo
                    .crearUa(it.llaveUA)
                    .crearCampania(it.unidadAdministrativa)
                    .crearIdPersona(it.id)
                metaRepository.guardar(metaSocia)
            }
        execute(complete, request.subscriberCompletable)
    }

    fun listar(request: Request) {
        val single = doOnSingle { obtenerPersona(request.personaId, request.rol) }
            .flatMap {
                metaRepository.listar(it)
            }
        execute(single, request.subscriberListSingle)
    }

    fun actualizar(request: Request) {
        val complete = doOnSingle { obtenerPersona(request.personaId, request.rol) }
            .flatMapCompletable {
                val metaSocia = request.modelo.crearCampania(it.unidadAdministrativa)
                metaRepository.actualizar(metaSocia)
            }
        execute(complete, request.subscriberCompletable)
    }

    fun eliminar(request: Request) {
        val complete = metaRepository.eliminar(request.modelo)
        execute(complete, request.subscriberCompletable)
    }

    private fun obtenerPersona(personaId: Long, personaRol: Rol) =
        requireNotNull(personaRepository.recuperarPersonaPorId(personaId, personaRol))

    class Request(
        val modelo: MetaSocia = MetaSocia.crearSinDatos(),
        val personaId: Long = -1,
        val rol: Rol = Rol.NINGUNO,
        val subscriberCompletable: BaseCompletableObserver = BaseCompletableObserver(),
        val subscriberListSingle: BaseSingleObserver<List<MetaSocia>> = BaseSingleObserver()
    )
}
