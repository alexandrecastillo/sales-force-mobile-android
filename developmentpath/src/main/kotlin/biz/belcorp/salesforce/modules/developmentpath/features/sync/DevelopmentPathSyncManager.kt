package biz.belcorp.salesforce.modules.developmentpath.features.sync

import android.util.Log
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DevelopmentPathSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.DEVELOPMENT_PATH_DASHBOARD,
        SyncGroup.HOME,
        SyncGroup.HOME_ON_LOGIN
    )

    override suspend fun start() {
        if (currentSyncGroup == SyncGroup.LOGIN && !isLoginSupport) {
            publish(SyncState.Updated)
        } else {
            try {
                publish(SyncState.Started)
                startLegacySync()
            } catch (e: Exception) {
                publish(SyncState.Failed(e))
                throw e
            }
        }
    }

    private suspend fun startLegacySync() {
        val syncType = awaitSync()
        if (syncType != SyncType.None) {
            publish(SyncState.Updated)
        }
    }

    private suspend fun awaitSync(): SyncType =
        suspendCancellableCoroutine { cont ->
            if (currentSyncGroup == SyncGroup.LOGIN && isLoginSupport) {
                syncUseCase.download(singleObserver(cont))
            } else if (currentSyncGroup != SyncGroup.LOGIN) {
                syncUseCase.sync(singleObserver(cont))
            }
        }

    private fun singleObserver(cont: CancellableContinuation<SyncType>) =
        object : BaseSingleObserver<SyncType>() {
            override fun onSuccess(t: SyncType) {
                cont.resume(t)
            }

            override fun onError(exception: Throwable) {
                cont.resumeWithException(exception)
            }
        }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.DEVELOPMENT_PATH_LEGACY_SYNC, state)
    }

}
