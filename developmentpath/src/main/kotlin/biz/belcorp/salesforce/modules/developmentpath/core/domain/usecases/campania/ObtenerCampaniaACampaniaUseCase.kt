package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.CampaniaCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.campania.CampaniaACampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class ObtenerCampaniaACampaniaUseCase(
    private val campaniaACampaniaRepository: CampaniaACampaniaRepository,
    private val rddPlanRepository: RddPlanRepository,
    private val personaRepository: RddPersonaRepository,
    private val campaniasUseCase: ObtenerCampaniasUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(request: Request, subscriber: BaseSingleObserver<Response>) {

        val identificador = PersonaRdd.Identificador(request.personaId, request.rol)
        val single = personaRepository.singleRecuperarPersonaPorId(identificador)
            .flatMap { persona ->
                campaniasUseCase.obtenerCampanias(persona.llaveUA)
                    .map { campaniaACampaniaRepository.obtener(persona, it) }
            }
            .flatMap { procesarCampaniaACampania(it, request) }

        execute(single, subscriber)
    }

    private fun procesarCampaniaACampania(
        campanias: List<CampaniaCampania>,
        request: Request
    ): Single<Response> {
        return campaniasUseCase.recuperarCampaniaUsuarioLogueado()
            .flatMap { ultimaCampaniaSesion ->
                permitirReconocer(campanias, request, ultimaCampaniaSesion)
            }
    }

    private fun permitirReconocer(
        campanias: List<CampaniaCampania>,
        request: Request,
        ultimaCampaniaSesion: Campania
    ): Single<Response> {
        return doOnSingle {

            val campaniaMasReciente = campanias.first()

            val cantidadVisitasRegistradas = rddPlanRepository.cantidadVisitasRegistradas(
                request.personaId,
                request.rol,
                ultimaCampaniaSesion.codigo
            )

            if (!tieneVisitaRegistrada(cantidadVisitasRegistradas)) {
                campaniaMasReciente.proveedorDeVisibilidad.mostrarCardHabilidades = false
                campaniaMasReciente.proveedorDeVisibilidad.mostrarReconocerHabilidad = false
            }
            obtenerResponse(campanias)
        }
    }

    private fun tieneVisitaRegistrada(cantidadVisitas: Long): Boolean {
        return cantidadVisitas > 0
    }

    private fun obtenerResponse(campaniaACampaniaList: List<CampaniaCampania>): Response {
        return Response(campaniaACampaniaList = campaniaACampaniaList)
    }

    class Request(
        val personaId: Long,
        val rol: Rol
    )

    class Response(val campaniaACampaniaList: List<CampaniaCampania>)
}
