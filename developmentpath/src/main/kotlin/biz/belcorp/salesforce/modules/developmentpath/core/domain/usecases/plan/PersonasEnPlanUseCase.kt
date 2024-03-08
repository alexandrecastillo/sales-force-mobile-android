package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import io.reactivex.Single
import io.reactivex.SingleObserver
import java.util.*

class PersonasEnPlanUseCase(
    private val rddUseCase: RddUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(request: Request) {
        val single = generarPlan(request.planId)
            .map { rdd -> extraerPersonasOrdenadas(rdd) }
            .map { personas -> convertirAPersonasResponse(personas) }

        execute(single, request.subscriber)
    }

    private fun generarPlan(planId: Long): Single<Rdd> {
        return Single.create { emitter ->
            val ruta = rddUseCase.generarRuta(planId)

            emitter.onSuccess(ruta)
        }
    }

    private fun extraerPersonasOrdenadas(rdd: Rdd): List<PersonaRdd> {
        return rdd.personasNoPlanificadas
            .extraerGRs()
            .sortedBy { it.region.codigo }
            .union(rdd.personasPlanificadas.sortedBy { it.fechaMostrada() })
            .toList()
    }

    private fun List<PersonaRdd>.extraerGRs(): List<GerenteRegionRdd> {
        return this.mapNotNull { it as? GerenteRegionRdd }
    }

    private fun convertirAPersonasResponse(personas: List<PersonaRdd>): List<PersonaResponse> {
        return personas.map { convertirAPersonaResponse(it) }
    }

    private fun convertirAPersonaResponse(persona: PersonaRdd): PersonaResponse {
        return PersonaResponse(
            persona = persona,
            visitaId = persona.agenda.primeraNoRegistrada?.id,
            planificada = persona.agenda.planificada,
            fechaPlanificacion = persona.fechaMostrada()
        )
    }

    private fun PersonaRdd.fechaMostrada(): Date? {
        return this.agenda.primeraNoRegistrada?.fechaAMostrar?.time
            ?: this.agenda.ultimaRegistrada?.fechaRegistro?.time
    }

    data class Request(
        val planId: Long,
        val subscriber: SingleObserver<List<PersonaResponse>>
    )

    data class PersonaResponse(
        val persona: PersonaRdd,
        val visitaId: Long?,
        val planificada: Boolean,
        val fechaPlanificacion: Date?
    )
}
