package biz.belcorp.salesforce.modules.brightpath.features.sync

import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.brightpath.core.domain.sync.SyncBrightPathUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class BrightPathSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncBrightPathUseCase>()

    override val groups = listOf(
        SyncGroup.HOME_ON_LOGIN,
        SyncGroup.KPI_EDA // Temporal
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            startLegacySync()
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private suspend fun startLegacySync() {
        val syncType = syncUseCase.sync()
        if (syncType != SyncType.None) {
            publish(SyncState.Updated)
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.BRIGHT_PATH_SYNC, state)
    }

}
