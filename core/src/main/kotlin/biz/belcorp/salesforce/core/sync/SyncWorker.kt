package biz.belcorp.salesforce.core.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.features.sync.CampaignsSyncManager
import biz.belcorp.salesforce.core.utils.getAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent


class SyncWorker(appContext: Context, private val workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams), KoinComponent {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            startSync()
            Result.success()
        } catch (e: Exception) {
            if (e is UnauthorizedException) {
                LiveDataBus.publish(
                    EventSubject.LOGOUT,
                    ConsumableEvent()
                )
            }
            Result.failure()
        }
    }

    private suspend fun startSync() {
        val groups = getSyncManagers().partition { it is CampaignsSyncManager }
        groups.first.firstOrNull()?.start()
        groups.second.forEach {
            it.start()
        }
    }

    private fun getSyncManagers(): List<SyncManager> {
        val syncGroup = getSyncGroup() ?: return emptyList()
        return getAll<SyncManager>()
            .filter { it.isAllowed(syncGroup) }
    }

    private fun getSyncGroup(): SyncGroup? {
        val key = SyncGroup::class.java.simpleName
        val groupName = workerParams.inputData.getString(key)
        return SyncGroup.values().find { it.name == groupName }
    }

}
