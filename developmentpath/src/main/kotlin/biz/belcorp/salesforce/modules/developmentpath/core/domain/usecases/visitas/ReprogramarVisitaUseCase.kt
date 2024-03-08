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
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
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

class ReprogramarVisitaUseCase(
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
    private lateinit var campania: Campania
    private lateinit var validadorTiempos: ValidadorTiempos

    fun replanificar(request: Request) {
        val observable = Observable.create<Date> { emitter ->
            recuperarDatosPlanificacion(request)
            validarTiemposCampania(request.fechaReplanificacion)
            disminuirVisitasPorFecha(request.fechaAnterior)
            reprogramarVisita(request.fechaReplanificacion)
            persistirReprogramacion()
            aumentarVisitasPorFecha(request.fechaReplanificacion)
            emitter.onNext(request.fechaReplanificacion)
            uploadVisitasRepository.syncUpRegisterVisits().doAsync()
        }
        execute(observable, request.subscriber)
    }

    private fun recuperarDatosPlanificacion(request: Request) {
        visita = obtenerVisita(request)
        campania = obtenerCampaniaActual()
        validadorTiempos = ValidadorTiempos(campania)
    }

    private fun obtenerVisita(request: Request): Visita {
        return requireNotNull(personaRepository.recuperarVisita(request.visitaId)) {
            Constant.VISIT_ID_NOT_VALID
        }
    }

    private fun validarTiemposCampania(fecha: Date) {
        validadorTiempos.validar(fecha)
    }

    private fun obtenerCampaniaActual(): Campania {
        return requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())) {
            Constant.INVALID_CAMPAIGN
        }
    }

    private fun disminuirVisitasPorFecha(fechaAnterior: Date) {
        val visitasPorFecha = obtenerOCrearVisitasPorFecha(fechaAnterior)
        visitasPorFecha.disminuirUno()
        visitasPorFechaRepository.guardar(visitasPorFecha)
    }

    private fun reprogramarVisita(fechaReplanificacion: Date) {
        visita.reprogramar(fechaReplanificacion.toCalendar())
    }

    private fun persistirReprogramacion() {
        personaRepository.actualizarVisita(visita)
    }

    private fun aumentarVisitasPorFecha(fechaReplanificacion: Date) {
        val visitasPorFecha = obtenerOCrearVisitasPorFecha(fechaReplanificacion)
        visitasPorFecha.aumentarUno()
        visitasPorFechaRepository.guardar(visitasPorFecha)
    }

    private fun obtenerOCrearVisitasPorFecha(fecha: Date): VisitasPorFecha {
        val planId = visita.planId
        val filtro = VisitasPorFechaRepository.Filtro(planId, fecha)
        return visitasPorFechaRepository.obtener(filtro)
                ?: VisitasPorFecha.crear(planId, fecha, recuperarMaxVisitas(planId))
    }

    private fun recuperarMaxVisitas(planId: Long): Int {
        val parametros = requireNotNull(configuracionRddRepository.get(planId)) {
            Constant.INVALID_RDD_CONFIGURATION
        }
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


    class Request(val visitaId: Long,
                  val fechaAnterior: Date,
                  val fechaReplanificacion: Date,
                  val subscriber: BaseObserver<Date>
    )
}
