package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doAsync
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator.ValidadorTiempos
import io.reactivex.Observable
import java.util.*

class PlanificarVisitaUseCase(
    private val uploadVisitasRepository: UploadVisitasRepository,
    private val sesionManager: SessionPersonRepository,
    private val visitasPorFechaRepository: VisitasPorFechaRepository,
    private val configuracionRddRepository: ConfigRddRepository,
    private val personaRepository: RddPersonaRepository,
    private val campaniaRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private lateinit var visita: Visita
    private lateinit var validadorTiempos: ValidadorTiempos
    private lateinit var campania: Campania

    fun planificarPersona(request: Request) {
        val observable = Observable.create<Date> { emitter ->
            recuperarDatosPlanificacion(request)
            validarPlanificacion(request.fecha)
            planificar(request.fecha)
            actualizarPlan()
            aumentarVisitasPorFecha(request.fecha)
            emitter.onNext(request.fecha)
            uploadVisitasRepository.syncUpRegisterVisits().doAsync()
        }
        execute(observable, request.subscriber)
    }

    private fun recuperarDatosPlanificacion(request: Request) {
        visita = obtenerVisita(request.visitaId)
        campania = obtenerCampaniaActual()
        validadorTiempos = ValidadorTiempos(campania)
    }

    private fun obtenerVisita(visitaId: Long) =
        checkNotNull(personaRepository.recuperarVisita(visitaId)) {
            "No se encontró la visita con id $visitaId"
        }

    private fun obtenerCampaniaActual(): Campania {
        val campania =
            requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())) {
                "Campaña inválida"
            }
        requireNotNull(campania.codigo) { "Código de campaña inválido" }
        return campania
    }

    private fun validarPlanificacion(fecha: Date) {
        validadorTiempos.validar(fecha)
    }

    private fun planificar(fecha: Date) {
        visita.programar(fecha.toCalendar())
    }

    private fun actualizarPlan() {
        personaRepository.actualizarVisita(visita)
    }

    private fun aumentarVisitasPorFecha(fecha: Date) {
        val visitasPorFecha = obtenerOCrearVisitasPorFecha(fecha)
        visitasPorFecha.aumentarUno()
        visitasPorFechaRepository.guardar(visitasPorFecha)
    }

    private fun obtenerOCrearVisitasPorFecha(fecha: Date): VisitasPorFecha {
        val planId = visita.planId
        val filtro = VisitasPorFechaRepository.Filtro(planId, fecha)
        return visitasPorFechaRepository.obtener(filtro)
            ?: VisitasPorFecha.crear(planId, fecha, obtenerMaxVisitasPorDefecto(visita.planId))
    }

    private fun obtenerMaxVisitasPorDefecto(planId: Long): Int {
        val parametros = configuracionRddRepository.get(planId)
            ?: throw Exception("Configuración RDD inválida")
        return parametros.maxVisitasPorDia
    }

    private fun recuperarLlaveUa(): LlaveUA {
        val sesion = requireNotNull(sesionManager.get())

        return LlaveUA(
            codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
            codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
            codigoSeccion = sesion.seccion.aGuionSiEsNullOVacio(),
            consultoraId = null
        )
    }


    class Request(
        val visitaId: Long,
        val fecha: Date,
        val subscriber: BaseObserver<Date>
    )
}
