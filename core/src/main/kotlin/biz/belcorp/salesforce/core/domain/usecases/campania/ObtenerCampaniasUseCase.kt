package biz.belcorp.salesforce.core.domain.usecases.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import io.reactivex.Single
import io.reactivex.SingleObserver

open class ObtenerCampaniasUseCase(
    private val repository: CampaniasRepository,
    private val sesionRepository: SessionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperarCampaniaActual(llaveUA: LlaveUA): Single<Campania> {
        return repository.obtenerCampaniaActualSingle(llaveUA)
    }

    fun recuperarCampaniaUsuarioLogueado(): Single<Campania> {
        return recuperarLLaveUa().flatMap { repository.obtenerCampaniaActualSingle(it) }
    }

    private fun recuperarLLaveUa(): Single<LlaveUA> {
        return doOnSingle { requireNotNull(sesionRepository.getSession()?.llaveUA) }
    }

    fun obtenerCampanias(llaveUA: LlaveUA): Single<List<Campania>> {
        return repository.obtenerCampanias(llaveUA.formatHyphenIfNull())
    }

    fun obtenerCampanias(subscribe: BaseSingleObserver<List<Campania>>) {
        val single = recuperarLLaveUa().flatMap { repository.obtenerCampanias(it) }
        execute(single, subscribe)
    }

    fun obtenerCampaniaActual(subscribe: BaseSingleObserver<Campania>) {
        val single = recuperarCampaniaUsuarioLogueado()
        execute(single, subscribe)
    }

    suspend fun obtenerCampaniaActual(): Campania {
        val llaveUA = requireNotNull(sesionRepository.getSession()?.llaveUA)
        return repository.obtenerCampaniaActualSuspend(llaveUA)
    }

    suspend fun getPreviousCampaignSuspend(uaKey: LlaveUA): Campania {
        return repository.getPreviousCampaignSuspend(uaKey)
    }

    fun getCampaignsSync(llaveUA: LlaveUA): List<Campania> {
        return repository.obtenerCampaniasSincrono(llaveUA)
    }



    suspend fun sync(): SyncType {
        val ua = requireNotNull(sesionRepository.getSession()).zonificacion
        return repository.sync(ua).also {
            sesionRepository.updateSession()
        }
    }

    suspend fun syncIfNeeded(): SyncType {
        return if (repository.minutesFromLastSync() >= repository.minutesCycleSync())
            sync()
        else
            SyncType.None
    }

    class Request {
        class RecuperarCampaniaActual(
            val personaId: Long,
            val rol: Rol,
            val subscriber: SingleObserver<Response.RecuperarCampaniaActual>
        )

        class GetLatestCampaigns(
            val personaId: Long,
            val rol: Rol,
            val subscriber: SingleObserver<Response.GetLatestCampaigns>
        )

    }

    class Response {
        class RecuperarCampaniaActual(val campania: Campania)
        class GetLatestCampaigns(val campaigns: List<Campania>)

    }

}
