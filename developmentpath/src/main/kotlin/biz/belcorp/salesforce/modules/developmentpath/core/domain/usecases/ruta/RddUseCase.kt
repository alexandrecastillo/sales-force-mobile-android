package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.config.Configurador
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.CronogramaEventos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Evento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.PlanificadorRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.hito.HitoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.FechaNoHabilRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.RecuperadorSugerencias
import io.reactivex.Observable

class RddUseCase(
    private val configRddRepository: ConfigRddRepository,
    private val campaniasRepository: CampaniasRepository,
    private val postulanteRepository: PosibleConsultoraRepository,
    private val hitoRepository: HitoRepository,
    private val fechaNoHabilRepository: FechaNoHabilRepository,
    private val eventosRepository: EventoRepository,
    private val visitasPorFechaRepository: VisitasPorFechaRepository,
    private val consultoraRDDRepository: ConsultoraRDDRepository,
    private val sociaEmpresariaRepository: SociaEmpresariaRepository,
    private val gerenteZonaRepository: GerenteZonaRepository,
    private val gerenteRegionRepository: GerenteRegionRepository,
    private val sesionManager: SessionPersonRepository,
    private val planRepository: RddPlanRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun planificar(request: Request) {
        val observable = Observable.create<Response> { emitter ->
            val infoPlan = planRepository.obtenerInfoPlanRdd(request.planId)
                ?: error("No hay rol del plan")

            val sesion = sesionManager.get()

            val ruta = this.generarRuta(request.planId)

            val response = Response(infoPlan, ruta, sesion)

            emitter.onNext(response)
            emitter.onComplete()
        }

        execute(observable, request.subscriber)
    }

    fun generarRuta(planId: Long): Rdd {
        val parametros = configRddRepository.get(planId)
            ?: error("No hay datos de configuración de RDD")

        val visitasPorFechas = visitasPorFechaRepository.obtener(planId)

        val infoPlan = planRepository.obtenerInfoPlanRdd(planId)
            ?: error("No hay información del plan")

        val campania = campaniasRepository.obtenerCampaniaActual(infoPlan.llaveUA)
            ?: error("No hay información de campaña")

        val eventos = obtenerEventos(infoPlan)

        val sesion = sesionManager.get()

        val planificables = obtenerPlanificables(
            rolLogueo = sesion.rol,
            rolDuenioPlan = infoPlan.rol,
            planId = planId
        )

        val planificador = PlanificadorRdd(
            configurador = Configurador(parametros, visitasPorFechas),
            campaniaActual = campania,
            personas = planificables,
            cronogramaEventos = CronogramaEventos(eventos),
            recuperadorSugerencias = RecuperadorSugerencias()
        )

        return planificador.generar()
    }

    private fun obtenerPlanificables(
        rolLogueo: Rol,
        rolDuenioPlan: Rol,
        planId: Long
    ): List<PersonaRdd> {
        val personas = mutableListOf<PersonaRdd>()

        if (rolLogueo == Rol.SOCIA_EMPRESARIA)
            personas.addAll(postulanteRepository.obtener(planId))

        when (rolDuenioPlan) {
            Rol.GERENTE_ZONA ->
                personas.addAll(sociaEmpresariaRepository.obtener(planId))
            Rol.GERENTE_REGION ->
                personas.addAll(gerenteZonaRepository.obtener(planId))
            Rol.DIRECTOR_VENTAS ->
                personas.addAll(gerenteRegionRepository.obtener(planId))
            else ->
                personas.addAll(consultoraRDDRepository.obtener(planId))
        }
        return personas
    }

    private fun obtenerEventos(infoPlanRdd: InfoPlanRdd): List<Evento> {
        return (obtenerHitos(infoPlanRdd) union
            fechaNoHabilRepository.obtener(infoPlanRdd.llaveUA) union
            eventosRepository.recuperar(infoPlanRdd.llaveUA)).toList()
    }

    private fun obtenerHitos(infoPlanRdd: InfoPlanRdd): List<Evento> {
        return when (infoPlanRdd.rol) {
            Rol.SOCIA_EMPRESARIA, Rol.GERENTE_ZONA ->
                hitoRepository.obtenerPorZona(requireNotNull(infoPlanRdd.codigoZona))
            Rol.GERENTE_REGION ->
                hitoRepository.obtenerPorRegion()
            Rol.DIRECTOR_VENTAS ->
                emptyList()
            else -> throw UnsupportedRoleException(infoPlanRdd.rol)
        }
    }

    class Request(
        val planId: Long,
        val subscriber: BaseObserver<Response>
    )

    class Response(
        val infoPlanRdd: InfoPlanRdd,
        val rdd: Rdd,
        val sesion: Sesion
    )
}
