package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico.AcuerdosHistoricos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico.AcuerdosPorCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class AcuerdosHistoricosUseCase(
    private val acuerdosRepository: AcuerdosRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val cantidadCampanias = 3
    private val acuerdosHistoricos = AcuerdosHistoricos()

    fun obtener(request: ObtenerRequest) {
        val single: Single<Response> = Single.create<Response> { emitter ->
            val persona = recuperarPersona(request)
            acuerdosHistoricos.acuerdos = recuperarAcuerdos(persona.llaveUA)
            emitter.onSuccess(generarResponse())
        }
        execute(single, request.subscriber)
    }

    fun invertirCumplimiento(request: InvertirRequest) {
        val single = Single.create<Response> { emitter ->
            val acuerdoModificado = acuerdosHistoricos.invertirCumplimiento(request.acuerdoId)
            acuerdosRepository.editar(acuerdoModificado)
            emitter.onSuccess(generarResponse())
        }

        execute(single, request.subscriber)
    }

    fun eventoRegistrarAnalytics(request: EventoAnalyticsRequest) {
        val single = doOnSingle {
            val acuerdo = acuerdosHistoricos.traerAcuerdo(request.acuerdoId)
            EventoAnalyticsResponse(acuerdo.cumplido, acuerdo.codigoCampania)
        }
        execute(single, request.subscriber)
    }

    private fun recuperarPersona(request: ObtenerRequest) =
        requireNotNull(
            personaRepository.recuperarPersonaPorId(
                request.personaId,
                request.rol
            )
        )

    private fun recuperarAcuerdos(llaveUA: LlaveUA) =
        acuerdosRepository
            .obtenerUltimasCampanias(llaveUA, cantidadCampanias)
            .sortedWith(comparadorAcuerdos())

    private fun comparadorAcuerdos() = compareByDescending<Acuerdo> { it.codigoCampania }
        .thenByDescending { it.fechaCreacion }

    private fun generarResponse(): Response {
        return Response(
            acuerdosHistoricos.acuerdosNoCumplidos,
            acuerdosHistoricos.acuerdosCumplidos,
            acuerdosHistoricos.acuerdosNoCumplidosAgrupados,
            acuerdosHistoricos.acuerdosCumplidosAgrupados
        )
    }

    data class ObtenerRequest(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<Response>
    )

    data class InvertirRequest(
        val acuerdoId: Long,
        val subscriber: SingleObserver<Response>
    )

    data class EventoAnalyticsRequest(
        val acuerdoId: Long,
        val subscriber: SingleObserver<EventoAnalyticsResponse>
    )

    data class EventoAnalyticsResponse(
        val cumplido: Boolean,
        val nombreCampania: String
    )

    data class Response(
        val acuerdosNoCumplidos: List<Acuerdo>,
        val acuerdosCumplidos: List<Acuerdo>,
        val acuerdosNoCumplidosAgrupados: List<AcuerdosPorCampania>,
        val acuerdosCumplidosAgrupados: List<AcuerdosPorCampania>
    )
}
