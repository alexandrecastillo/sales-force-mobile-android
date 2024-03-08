package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.core.utils.suprimirAntesAGuion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.OrdenadorDesempenio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.desempenio.DesempenioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants.PREFIJO_CAMPANIA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallelWithResult
import io.reactivex.Single

class ObtenerDesempenioGrGzUseCase(
    private val desempenioRepository: DesempenioRepository,
    private val personaRepository: RddPersonaRepository,
    private val campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(request: Request) {

        val identificador = PersonaRdd.Identificador(request.personaId, request.rol)
        val single = personaRepository.singleRecuperarPersonaPorId(identificador)
            .doInParallelWithResult(
                { campaniasRepository.obtenerCampanias(it.llaveUA.formatHyphenIfNull()) },
                { obtenerDesempeniosU6C(it) },
                { campanias, desempenios -> completarDesempenios(desempenios, campanias) })
            .map { obtenerResponse(it) }

        execute(single, request.subscriber)
    }

    private fun completarDesempenios(
        desempenios: List<DesempenioCampanias>,
        campanias: List<Campania>
    ):
        Single<List<DesempenioCampanias>> {
        return doOnSingle {
            val penultimasCampanias = campanias.takeLast(6)
            require(penultimasCampanias.size == desempenios.size)

            penultimasCampanias.forEachIndexed { i, campania ->
                desempenios[i].campania =
                    "$PREFIJO_CAMPANIA ${campania.nombreCorto.suprimirAntesAGuion()}"
            }
            desempenios
        }
    }

    private fun obtenerDesempeniosU6C(persona: PersonaRdd): Single<List<DesempenioCampanias>> {
        return doOnSingle {
            when (persona) {
                is GerenteZonaRdd -> desempenioRepository.obtener(persona.llaveUA.formatHyphenIfNull())
                is GerenteRegionRdd -> desempenioRepository.obtener(persona.llaveUA.formatHyphenIfNull())
                else -> throw UnsupportedRoleException(persona.rol)
            }
        }
    }

    private fun obtenerResponse(desempenioU6C: List<DesempenioCampanias>): Response {
        val nuevaLista = OrdenadorDesempenio<DesempenioCampanias>().reordenar(desempenioU6C)
        return Response(nuevaLista.toList())
    }

    class Request(
        val personaId: Long,
        val rol: Rol,
        val subscriber: BaseSingleObserver<Response>
    )

    class Response(val desempenioU6C: List<DesempenioCampanias>)
}
