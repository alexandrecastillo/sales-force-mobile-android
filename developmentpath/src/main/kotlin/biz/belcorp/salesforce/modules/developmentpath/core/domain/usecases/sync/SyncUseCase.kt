package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.SyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.ListadoFocosEnAsignacionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.HorarioVisitaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.IngresosExtraRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaConsultoraRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaPersonalRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.SolicitarEventosCambiadosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.SincronizadorFocos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.SincronizadorAsignarHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.SincronizadorReconocerHabilidades
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SyncUseCase(
    private val uploadVisitas: UploadVisitasRepository,
    private val listadoFocosRepository: ListadoFocosEnAsignacionRepository,
    private val eventosRddRepository: EventoRepository,
    private val eventosXUaRepository: EventoXUaRepository,
    private val sincronizadorFocos: SincronizadorFocos,
    private val sincronizadorAsignarHabilidades: SincronizadorAsignarHabilidades,
    private val sincronizadorReconocerHabilidades: SincronizadorReconocerHabilidades,
    private val uploadMetas: MetaConsultoraRepository,
    private val anotacionesRepository: MetaPersonalRepository,
    private val comportamientoRepository: ReconocimientosRepository,
    private val acuerdosRepository: AcuerdosRepository,
    private val sesionManager: SessionPersonRepository,
    private val syncRepository: SyncRepository,
    private val solicitarEventosCambiadosUseCase: SolicitarEventosCambiadosUseCase,
    private val intencionPedidoRepository: IntencionPedidoRepository,
    private val ingresosExtraRepository: IngresosExtraRepository,
    private val horarioVisitaRepository: HorarioVisitaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun subirPendientes(): Completable {
        return Completable.merge(listarUploads())
    }

    fun sync(observer: BaseSingleObserver<SyncType>) {
        val single = subirPendientes().andThen(descargarDatos())
        execute(single, observer)
    }

    fun download(observer: BaseSingleObserver<SyncType>) {
        val single = descargarDatos()
        execute(single, observer)
    }

    private fun descargarDatos(): Single<SyncType> {
        val ua = sesionManager.get().zonificacion
        val campaign = sesionManager.get().campaign
        val rol = sesionManager.get().rol
        return syncRepository.sync(ua, campaign, rol)
            .flatMap { decidirDescargarEventos(it) }
    }

    private fun decidirDescargarEventos(tipo: SyncType): Single<SyncType> {
        return if (tipo !is SyncType.FullyUpdated) {
            descargarEventos().andThen(doOnSingle { tipo })
        } else {
            doOnSingle { tipo }
        }
    }

    private fun descargarEventos(): Completable {
        return solicitarEventosCambiadosUseCase.solicitarSinObserver().toCompletable()
    }

    private fun listarUploads() =
        listOf(
            listadoFocosRepository.sincronizar(),
            uploadMetas.sincronizar(),
            uploadVisitas.syncUpRegisterVisits(),
            uploadVisitas.syncUpAdditionalVisits(),
            anotacionesRepository.sincronizar(),
            acuerdosRepository.sincronizar(),
            comportamientoRepository.sincronizar(),
            sincronizadorFocos.sincronizar(),
            sincronizadorAsignarHabilidades.sincronizar(),
            sincronizadorReconocerHabilidades.sincronizar(),
            eventosRddRepository.enviarAServidor(),
            eventosXUaRepository.enviarAServidor(),
            intencionPedidoRepository.sincronizar(),
            ingresosExtraRepository.sincronizar(),
            horarioVisitaRepository.sincronizar()
                .andThen(horarioVisitaRepository.updateHorarioVisitaConsultora())
        )

    suspend fun uploadProfileInfo() {
        suspendCancellableCoroutine<Boolean> { cont ->
            Completable.merge(listarUploads())
                .subscribe({
                    cont.resume(true)
                }, { exception ->
                    cont.resumeWithException(exception)
                })
        }
    }

}
