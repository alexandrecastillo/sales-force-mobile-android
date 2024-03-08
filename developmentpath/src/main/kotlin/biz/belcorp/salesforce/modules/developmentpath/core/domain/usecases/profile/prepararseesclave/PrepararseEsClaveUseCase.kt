package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.PrepararseEsClaveRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallelWithResult
import io.reactivex.Single

class PrepararseEsClaveUseCase(
    private val prepararseEsClaveRepository: PrepararseEsClaveRepository,
    private val personaRepository: RddPersonaRepository,
    private val campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerDatos(request: Request) {
        val identificador = PersonaRdd.Identificador(request.personaId, request.rol)
        val single = personaRepository.singleRecuperarPersonaPorId(identificador)
            .doInParallelWithResult(
                { obtenerCampaniaAnterior(it) },
                { obtenerElementosPrepararseEsClave(it) },
                { campania, elementosPrepararseEsClave -> createElementosPrepararEsClave(Params(request.rol, campania, elementosPrepararseEsClave)) })

        execute(single, request.subscriber)
    }

    private fun obtenerCampaniaAnterior(persona: PersonaRdd): Single<Campania?> {
        return doOnSingle { campaniasRepository.obtenerCampaniaInmediataAnterior(persona.llaveUA) }
    }

    private fun obtenerElementosPrepararseEsClave(persona: PersonaRdd): Single<List<PrepararseEsClave>> {
        return doOnSingle { prepararseEsClaveRepository.obtenerElementosPorRol(persona.rol) }
    }

    private fun createElementosPrepararEsClave(params: Params): Single<List<PrepararseEsClave>> {
        return doOnSingle {
            when (params.rol) {
                Rol.SOCIA_EMPRESARIA -> reemplazarNombreCampania(params.campania, params.elementos)
                else -> params.elementos
            }
        }
    }

    private fun reemplazarNombreCampania(campania: Campania?, elementos: List<PrepararseEsClave>): List<PrepararseEsClave> {
        return prepararseEsClaveRepository.reemplazarCampaniaEnElementoSocia(campania?.nombreCorto
            ?: Constants.EMPTY_STRING, elementos)
    }

    private class Params(val rol: Rol, val campania: Campania?, val elementos: List<PrepararseEsClave>)

    class Request(val personaId: Long,
                  val rol: Rol,
                  val subscriber: BaseSingleObserver<List<PrepararseEsClave>>
    )
}
