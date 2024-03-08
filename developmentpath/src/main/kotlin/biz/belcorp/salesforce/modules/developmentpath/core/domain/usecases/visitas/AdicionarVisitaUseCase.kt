package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.ModeloCreacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips.TipsVisitaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doAsync
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator.ValidadorTiempos
import io.reactivex.Observable
import java.util.*

class AdicionarVisitaUseCase(
    private val uploadVisitasRepository: UploadVisitasRepository,
    private val sesionManager: SessionPersonRepository,
    private val visitasPorFechaRepository: VisitasPorFechaRepository,
    private val configuracionRddRepository: ConfigRddRepository,
    private val personaRepository: RddPersonaRepository,
    private val rddPlanRepository: RddPlanRepository,
    private val tipsVisitaRepository: TipsVisitaRepository,
    private val campaniaRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private lateinit var persona: PersonaRdd
    private lateinit var infoPlanRdd: InfoPlanRdd
    private lateinit var campania: Campania
    private lateinit var validadorTiempos: ValidadorTiempos

    fun adicionarVisita(request: Request) {
        val observable = Observable.create<Date> { emitter ->
            recuperarDatosPlanificacion(request)
            validarAdicion(request.fecha)
            crearVisita(request.fecha)
            aumentarVisitasPorFecha(request.fecha)
            emitter.onNext(request.fecha)
            uploadVisitasRepository.syncUpAdditionalVisits().doAsync()
        }
        execute(observable, request.subscriber)
    }

    private fun recuperarDatosPlanificacion(request: Request) {
        persona = obtenerPersona(request.identificador)
        infoPlanRdd = obtenerInfoPlanAlQuePertenecePersona(request.identificador)
        campania = obtenerCampania()
        validadorTiempos = ValidadorTiempos(campania)
    }

    private fun obtenerPersona(identificador: PersonaRdd.Identificador): PersonaRdd {
        return requireNotNull(personaRepository.recuperarPersona(identificador))
    }

    private fun obtenerInfoPlanAlQuePertenecePersona(identificador: PersonaRdd.Identificador): InfoPlanRdd {
        return requireNotNull(
            rddPlanRepository.obtenerPlanAlQuePerteneceUnaPersona(identificador)
        )
    }

    private fun obtenerCampania(): Campania {
        return requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())) { "Camapaña inválida" }
    }

    private fun obtenerIdDeCabeceraDeTips(identificador: PersonaRdd.Identificador) =
        checkNotNull(tipsVisitaRepository.obtenerIdCabeceraTip(identificador.id, identificador.rol))

    private fun validarAdicion(fecha: Date) {
        validadorTiempos.validar(fecha)
    }

    private fun crearVisita(fecha: Date) {
        val tipsId = obtenerIdDeCabeceraDeTips(persona.identificador)
        val modeloCreacionVisita = ModeloCreacionVisita(
            planId = infoPlanRdd.planId,
            tipsId = tipsId,
            fechaPlanificacion = fecha,
            persona = persona,
            esAdicional = true
        )
        personaRepository.crearVisita(modeloCreacionVisita)
    }

    private fun aumentarVisitasPorFecha(fecha: Date) {
        val visitasPorFecha = obtenerOCrearVisitasPorFecha(fecha)
        visitasPorFecha.aumentarUno()
        visitasPorFechaRepository.guardar(visitasPorFecha)
    }

    private fun obtenerOCrearVisitasPorFecha(fecha: Date): VisitasPorFecha {
        val filtro = VisitasPorFechaRepository.Filtro(infoPlanRdd.planId, fecha)
        return visitasPorFechaRepository.obtener(filtro)
            ?: VisitasPorFecha.crear(infoPlanRdd.planId, fecha, obtenerMaxVisitasPorDefecto())
    }

    private fun obtenerMaxVisitasPorDefecto(): Int {
        val parametros = configuracionRddRepository.get(infoPlanRdd.planId)
            ?: throw Exception("Configuración RDD inválida")
        return parametros.maxVisitasPorDia
    }

    class Request(
        val identificador: PersonaRdd.Identificador,
        val fecha: Date,
        val subscriber: BaseObserver<Date>
    )

    private fun recuperarLlaveUa(): LlaveUA {
        val sesion = requireNotNull(sesionManager.get())

        return LlaveUA(
            codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
            codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
            codigoSeccion = sesion.seccion.aGuionSiEsNullOVacio(),
            consultoraId = null
        )
    }
}
