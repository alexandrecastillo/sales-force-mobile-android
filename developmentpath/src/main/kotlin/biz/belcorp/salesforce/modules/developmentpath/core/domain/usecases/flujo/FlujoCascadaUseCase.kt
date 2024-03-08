package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.DashboardRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doAsync
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class FlujoCascadaUseCase(
    private val rddPlanRepository: RddPlanRepository,
    private val sesionManager: SessionPersonRepository,
    private val syncUseCase: SyncUseCase,
    private val dashboardRepository: DashboardRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private val resultadoPorRol = mapOf(
        Rol.GERENTE_REGION to ResultadoCascada.IR_A_DASHBOARD_GR,
        Rol.GERENTE_ZONA to ResultadoCascada.IR_A_DASHBOARD_GZ,
        Rol.SOCIA_EMPRESARIA to ResultadoCascada.IR_A_MI_RUTA
    )

    private lateinit var sesion: Sesion
    private lateinit var llaveUA: LlaveUA
    private var infoPlan: InfoPlanRdd? = null

    fun ejecutar(request: Request) {
        val observable = Observable.create<Response> { emitter ->
            recuperarDataInicial(request.llaveUA)
            descargarADemandaOContinuarFlujoOffline(emitter)
        }

        execute(observable, request.subscriber)
    }

    private fun mostrarCargando(emitter: ObservableEmitter<Response>) {
        emitter.onNext(generarResponseMostrarCargando())
    }

    private fun descargarADemandaOContinuarFlujoOffline(emitter: ObservableEmitter<Response>) {
        if (seDebeTraerDashboardADemanda()) {
            mostrarCargando(emitter)
            descargarADemanda(emitter)
        } else {
            continuarFlujoOffline(emitter)
        }
    }

    private fun descargarADemanda(emitter: ObservableEmitter<Response>) {
        subirDatosPendientes()
        descargarDashboard(emitter)
    }

    private fun continuarFlujoOffline(emitter: ObservableEmitter<Response>) {
        emitter.onNext(generarResponse())
        emitter.onComplete()
    }

    private fun ObservableEmitter<Response>.comunicarDescargaExitosa() {
        onNext(generarResponse())
        onNext(generarResponseOcultarCargando())
        onComplete()
    }

    private fun recuperarDataInicial(llaveUA: LlaveUA) {
        sesion = requireNotNull(sesionManager.get())
        //Si no encuentra un plan (null), el servicio intentará generar el plan según la ua
        infoPlan = rddPlanRepository.obtenerInfoPlanRdd(llaveUA)
        this.llaveUA = llaveUA
    }

    private fun seDebeTraerDashboardADemanda(): Boolean {
        //No se obtiene el dashboard de SE a demanda,
        //pues redirige a la pantalla 'mi ruta' directamente
        return sesion.rol == Rol.DIRECTOR_VENTAS &&
            llaveUA.rolLiderAsociado != Rol.SOCIA_EMPRESARIA
    }

    private fun subirDatosPendientes() {
        syncUseCase.subirPendientes().doAsync()
    }

    private fun generarResponse(): Response {
        val plan = infoPlan ?: throw InvalidPlanException(llaveUA.rolLiderAsociado)
        val direccion = requireNotNull(resultadoPorRol[llaveUA.rolLiderAsociado])

        return Response(plan.planId, direccion)
    }

    private fun generarResponseMostrarCargando() =
        Response(
            planId = -1L,
            direccion = ResultadoCascada.MOSTRAR_CARGANDO
        )

    private fun generarResponseOcultarCargando() =
        Response(
            planId = -1L,
            direccion = ResultadoCascada.OCULTAR_CARGANDO
        )

    private fun generarResponseRecargarDashboard() =
        Response(
            planId = -1L,
            direccion = ResultadoCascada.RECARGAR_DASHBOARD_ORIGEN
        )

    private fun descargarDashboard(emitter: ObservableEmitter<Response>) {
        val request = DashboardRepository.Request(infoPlan?.planId, llaveUA)

        dashboardRepository
            .sincronizar(request)
            .subscribe({ validarRespuestaSincronizacion(emitter) },
                { throwable -> emitter.onError(throwable) })
    }

    private fun validarRespuestaSincronizacion(emitter: ObservableEmitter<Response>) {

        val nuevoInfoPlan = rddPlanRepository.obtenerInfoPlanRdd(llaveUA)

        if (nuevoInfoPlan == null) {
            emitter.onError(InvalidPlanException(llaveUA.rolLiderAsociado))
            return
        } else {
            // Si se puede get el plan desde la bd después de sincronizar, se recarga el avance.
            infoPlan = nuevoInfoPlan
            emitter.onNext(generarResponseRecargarDashboard())
            emitter.comunicarDescargaExitosa()
        }
    }

    class Request(
        val llaveUA: LlaveUA,
        val subscriber: BaseObserver<Response>
    )

    class Response(
        val planId: Long,
        val direccion: ResultadoCascada
    )

    class InvalidPlanException(val rolUA: Rol) :
        Exception("La unidad administrativa no ha sido planificada.")

    enum class ResultadoCascada {
        IR_A_DASHBOARD_GR,
        IR_A_DASHBOARD_GZ,
        IR_A_MI_RUTA,
        MOSTRAR_CARGANDO,
        OCULTAR_CARGANDO,
        RECARGAR_DASHBOARD_ORIGEN
    }
}
