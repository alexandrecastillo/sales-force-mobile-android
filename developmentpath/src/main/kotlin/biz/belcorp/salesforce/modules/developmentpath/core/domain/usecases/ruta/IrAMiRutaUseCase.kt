package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.diferenciaDeNivel
import biz.belcorp.salesforce.core.utils.execCompletableCoroutine
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.PlanDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase
import io.reactivex.Completable
import io.reactivex.Observable

class IrAMiRutaUseCase constructor(
    private val planRepository: RddPlanRepository,
    private val planDetalleRepository: PlanDetalleRepository,
    private val consultantsSyncRepository: ConsultantsSyncRepository,
    private val campaniasRepository: CampaniasRepository,
    private val sesionManager: SessionPersonRepository,
    private val syncUseCase: SyncUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(
    threadExecutor,
    postExecutionThread
) {

    companion object {
        private const val DIFERENCIA_MAXIMA_CARGA_A_DEMANDA_DV = 1
        private const val DIFERENCIA_MAXIMA_CARGA_A_DEMANDA_OTROS = 2
    }

    fun ejecutar(planId: Long, subscriber: BaseObserver<Response>) {
        val observable = Observable.create<Response> { emitter ->

            val infoPlan = checkNotNull(planRepository.obtenerInfoPlanRdd(planId))
            val sesion = checkNotNull(sesionManager.get())

            if (seDebeDescargarADemanda(sesion, infoPlan)) {

                emitter.onNext(Response.MostrarCargando())

                descargarADemanda(infoPlan)
                    .subscribe({
                        emitter.onNext(Response.OcultarCargando())
                        emitter.onNext(Response.IrARuta(planId))
                        emitter.onComplete()
                    }, { throwable ->
                        emitter.onError(throwable)
                    })
            } else {
                emitter.onNext(Response.IrARuta(planId))
                emitter.onComplete()
            }
        }

        execute(observable, subscriber)
    }

    private fun seDebeDescargarADemanda(
        sesion: Sesion,
        infoPlan: InfoPlanRdd
    ): Boolean {
        return when (sesion.rol) {
            Rol.DIRECTOR_VENTAS ->
                sesion.rol diferenciaDeNivel infoPlan.rol >= DIFERENCIA_MAXIMA_CARGA_A_DEMANDA_DV
            Rol.GERENTE_REGION,
            Rol.GERENTE_ZONA,
            Rol.SOCIA_EMPRESARIA ->
                sesion.rol diferenciaDeNivel infoPlan.rol >= DIFERENCIA_MAXIMA_CARGA_A_DEMANDA_OTROS
            else -> throw UnsupportedRoleException(sesion.rol)
        }
    }

    private fun descargarADemanda(infoPlan: InfoPlanRdd): Completable {
        return syncUseCase
            .subirPendientes()
            .andThen(planDetalleRepository.sincronizar(infoPlan.planId))
            .andThen(syncConsultants(infoPlan))
    }

    private fun syncConsultants(infoPlan: InfoPlanRdd): Completable = with(infoPlan) {
        execCompletableCoroutine {
            val campaign = requireNotNull(campaniasRepository.obtenerCampaniaActual(llaveUA))
            val params = SyncParams(llaveUA, codigoPais, campaign.codigo, campaign.getPhase(), isForced = true)
            consultantsSyncRepository.sync(params)
        }
    }

    sealed class Response {
        class MostrarCargando : Response()
        class OcultarCargando : Response()
        class IrARuta(val planId: Long) : Response()
    }
}
