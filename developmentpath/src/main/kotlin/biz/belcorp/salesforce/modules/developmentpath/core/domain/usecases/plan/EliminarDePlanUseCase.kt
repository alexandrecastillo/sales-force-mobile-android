package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import io.reactivex.Observable
import java.util.*

class EliminarDePlanUseCase(private val uploadVisitasRepository: UploadVisitasRepository,
                            private val visitasPorFechaRepository: VisitasPorFechaRepository,
                            private val configuracionRddRepository: ConfigRddRepository,
                            private val personaRepository: RddPersonaRepository,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun eliminarDePlan(request: Request) {

        val observable = Observable.create<Unit> { emitter ->
            val visita = obtenerVisita(request)

            disminuirVisitasPorFecha(visita, request.fechaOriginal)
            visita.eliminar()
            personaRepository.actualizarVisita(visita)
            emitter.onNext(Unit)
            uploadVisitasRepository.syncUpRegisterVisits().subscribe({}, {})
        }

        execute(observable, request.subscriber)
    }

    private fun obtenerVisita(request: EliminarDePlanUseCase.Request) =
        checkNotNull(personaRepository.recuperarVisita(request.visitaId)) {
            "No se encontró visita con id: ${request.visitaId}"
        }

    private fun disminuirVisitasPorFecha(visita: Visita, fecha: Date) {
        val visitasPorFecha = obtenerOCrearVisitasPorFecha(visita, fecha)
        visitasPorFecha.disminuirUno()

        visitasPorFechaRepository.guardar(visitasPorFecha)
    }

    private fun obtenerOCrearVisitasPorFecha(visita: Visita,
                                             fecha: Date
    ): VisitasPorFecha {
        val planId = visita.planId
        val filtro = VisitasPorFechaRepository.Filtro(planId, fecha)

        return visitasPorFechaRepository.obtener(filtro) ?: VisitasPorFecha.crear(
            planId,
            fecha,
            obtenerMaxVisitasPorDefecto(visita.planId))
    }

    private fun obtenerMaxVisitasPorDefecto(planId: Long): Int {
        val parametros = configuracionRddRepository.get(planId)
            ?: throw Exception("Configuración RDD inválida")

        return parametros.maxVisitasPorDia
    }

    class Request(val visitaId: Long,
                  val fechaOriginal: Date,
                  val subscriber: BaseObserver<Unit>
    )
}
